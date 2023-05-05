package uz.gita.dima.firebasefirestorepractise.domain

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.firebasefirestorepractise.data.Image

interface AppRepository {

    fun getAllImages(): Flow<Result<List<Image>>>

    fun uploadImage(uri: Uri): Flow<Result<String>>
    fun deleteImage(image: Image): Flow<Result<String>>
}