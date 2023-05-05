package uz.gita.dima.firebasefirestorepractise.domain.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.dima.firebasefirestorepractise.data.Image
import uz.gita.dima.firebasefirestorepractise.domain.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor() : AppRepository {

    private var storageRef = FirebaseStorage.getInstance().reference.child("Images")
    private var firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun getAllImages(): Flow<Result<List<Image>>> = callbackFlow {
        firebaseFireStore.collection("images")
            .get().addOnSuccessListener {
                val mList = mutableListOf<Image>()
                for (i in it) {
                    val a = i.reference.path
                    val data = Image(a, i.data["pic"].toString())
                    mList.add(data)
                }
                trySend(Result.success(mList))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun uploadImage(uri: Uri): Flow<Result<String>> = callbackFlow {
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        uri.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val map = HashMap<String, Any>()
                    map["pic"] = uri.toString()
                    Log.d("TTT", "Image Uri -> ${uri.toString()}")

                    firebaseFireStore.collection("images").add(map)
                        .addOnCompleteListener { firestoreTask ->

                            if (firestoreTask.isSuccessful) {
                                trySend(Result.success("Success"))
                            } else {
                                trySend(Result.success("Fail"))
                            }
                        }
                }
            }
        }
        awaitClose()
    }

    override fun deleteImage(image: Image): Flow<Result<String>> = callbackFlow {
        firebaseFireStore
            .document(image.idDoc)
            .delete()
            .addOnSuccessListener {
                trySend(Result.success("Deleted"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }
}