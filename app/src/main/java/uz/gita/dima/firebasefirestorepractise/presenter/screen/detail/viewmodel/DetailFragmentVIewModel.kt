package uz.gita.dima.firebasefirestorepractise.presenter.screen.detail.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dima.firebasefirestorepractise.data.Image

interface DetailFragmentVIewModel {
    val progress: LiveData<Boolean>
    val allImages: LiveData<List<Image>>
    val errorMessage: LiveData<String>
    val deleteMessage: LiveData<String>
    val deleteMessageError: LiveData<String>
    fun getAllImages()
    fun deleteImage(image: Image)
}