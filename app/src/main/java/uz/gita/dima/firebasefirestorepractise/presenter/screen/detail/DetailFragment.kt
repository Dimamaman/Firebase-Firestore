package uz.gita.dima.firebasefirestorepractise.presenter.screen.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.firebasefirestorepractise.R
import uz.gita.dima.firebasefirestorepractise.data.Image
import uz.gita.dima.firebasefirestorepractise.databinding.DetailScreenBinding
import uz.gita.dima.firebasefirestorepractise.presenter.adapter.ImagesAdapter
import uz.gita.dima.firebasefirestorepractise.presenter.screen.detail.viewmodel.DetailFragmentVIewModel
import uz.gita.dima.firebasefirestorepractise.presenter.screen.detail.viewmodel.impl.DetailFragmentViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_screen) {
    private val binding by viewBinding(DetailScreenBinding::bind)
    private val viewModel: DetailFragmentVIewModel by viewModels<DetailFragmentViewModelImpl>()

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllImages()
        viewModel.progress.observe(viewLifecycleOwner, progressObserver)
        viewModel.allImages.observe(viewLifecycleOwner, allImagesObserver)
        viewModel.deleteMessage.observe(viewLifecycleOwner, deleteMessageObserver)
        viewModel.deleteMessageError.observe(viewLifecycleOwner, deleteMessageObserverError)
        binding.recyclerView.adapter = imagesAdapter

        imagesAdapter.clickLong {
            viewModel.deleteImage(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val allImagesObserver = Observer<List<Image>> {
        Log.d("TTT", "All List -> $it")
        imagesAdapter.submitList(it)
      //  imagesAdapter.notifyDataSetChanged()
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private val deleteMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()
    }

    private val deleteMessageObserverError = Observer<String> {
        Toast.makeText(requireContext(), "Error Deleted", Toast.LENGTH_SHORT).show()
    }
}