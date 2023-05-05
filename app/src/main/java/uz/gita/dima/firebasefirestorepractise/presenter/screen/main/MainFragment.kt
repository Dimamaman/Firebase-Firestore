package uz.gita.dima.firebasefirestorepractise.presenter.screen.main

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.firebasefirestorepractise.R
import uz.gita.dima.firebasefirestorepractise.databinding.MainScreenBinding
import uz.gita.dima.firebasefirestorepractise.presenter.screen.main.viewmodel.MainViewModel
import uz.gita.dima.firebasefirestorepractise.presenter.screen.main.viewmodel.impl.MainViewModelImpl

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_screen) {
    private var clicked = false
    private var currentFile: Uri? = null
    private val binding by viewBinding(MainScreenBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        clickEvents()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.success.observe(this@MainFragment, successObserver)
        viewModel.progress.observe(this@MainFragment, progressObserver)
        viewModel.openDetailFragment.observe(this@MainFragment, openDetailFragmentObserver)
    }

    private val successObserver = Observer<String> {
        Toast.makeText(requireContext(), "Uploded Successfully", Toast.LENGTH_SHORT).show()
        clicked = false
        binding.imageView.setImageResource(R.drawable.vector)
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        clicked = false
        binding.imageView.setImageResource(R.drawable.vector)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private val openDetailFragmentObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    private fun clickEvents() {
        binding.uploadBtn.setOnClickListener {
            if (clicked) {
                currentFile?.let { it1 -> viewModel.uploadImage(it1) }
            } else {
                Toast.makeText(requireContext(), "Rasmni ustiga bosing", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView.setOnClickListener {
            clicked = true
            resultLauncher.launch("image/*")
        }

        binding.showAllBtn.setOnClickListener {
            viewModel.openDetailFragment()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        currentFile = it
        binding.imageView.setImageURI(it)
    }
}