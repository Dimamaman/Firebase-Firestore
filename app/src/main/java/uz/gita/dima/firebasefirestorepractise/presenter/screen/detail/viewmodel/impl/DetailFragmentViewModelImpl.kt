package uz.gita.dima.firebasefirestorepractise.presenter.screen.detail.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.firebasefirestorepractise.data.Image
import uz.gita.dima.firebasefirestorepractise.domain.AppRepository
import uz.gita.dima.firebasefirestorepractise.presenter.screen.detail.viewmodel.DetailFragmentVIewModel
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModelImpl @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(), DetailFragmentVIewModel {

    override val progress = MutableLiveData<Boolean>()
    override val allImages = MutableLiveData<List<Image>>()
    override val errorMessage = MutableLiveData<String>()
    override val deleteMessage = MutableLiveData<String>()
    override val deleteMessageError = MutableLiveData<String>()

    override fun getAllImages() {
        progress.value = true
        appRepository.getAllImages().onEach {

            it.onSuccess {
                allImages.value = it
                progress.value = false
            }

            it.onFailure {
                errorMessage.value = it.message
                progress.value = false
            }
        }.launchIn(viewModelScope)
    }

    override fun deleteImage(image: Image) {
        appRepository.deleteImage(image).onEach {
            it.onSuccess {
                deleteMessage.value = it
                getAllImages()
            }
            it.onFailure {
                deleteMessageError.value = it.message
            }
        }.launchIn(viewModelScope)
    }
}