package com.example.nasaapod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.nasaapod.R
import com.example.nasaapod.databinding.MarsFragmentBinding
import com.example.nasaapod.domain.MarsRepositoryImp
import com.example.nasaapod.ui.viewmodel.MarsViewModel

class MarsFragment:Fragment (R.layout.mars_fragment) {

    private lateinit var binding: MarsFragmentBinding

    private val marsViewModel: MarsViewModel by viewModels {
        MarsViewModel.MarsViewModelFactory(MarsRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            marsViewModel.requestMars()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MarsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            marsViewModel.loading.collect{

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            marsViewModel.title.collect { title ->
                title?.let {
                    binding.textFragMars.text = title
                }
            }
        }

    }
}