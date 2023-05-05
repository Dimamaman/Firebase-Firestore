package uz.gita.dima.firebasefirestorepractise.presenter.screen.main.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData

interface MainViewModel {
    val error: LiveData<String>
    val success: LiveData<String>
    val progress: LiveData<Boolean>
    val openDetailFragment: LiveData<Unit>

    fun uploadImage(uri: Uri)
    fun openDetailFragment()
}