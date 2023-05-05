package uz.gita.dima.firebasefirestorepractise.presenter.screen.main.viewmodel.impl

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.firebasefirestorepractise.domain.AppRepository
import uz.gita.dima.firebasefirestorepractise.presenter.screen.main.viewmodel.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val appRepository: AppRepository
): ViewModel(), MainViewModel {
    override val error = MutableLiveData<String>()
    override val success = MutableLiveData<String>()
    override val progress = MutableLiveData<Boolean>()
    override val openDetailFragment = MutableLiveData<Unit>()


    override fun uploadImage(uri: Uri) {
        progress.value = true
        appRepository.uploadImage(uri).onEach {
            it.onSuccess {
                success.value = it
                progress.value = false
            }

            it.onFailure {
                error.value = it.message
                progress.value = false
            }
        }.launchIn(viewModelScope)
    }

    override fun openDetailFragment() {
        openDetailFragment.value = Unit
    }
}