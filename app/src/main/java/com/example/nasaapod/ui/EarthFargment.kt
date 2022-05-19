package com.example.nasaapod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.nasaapod.R
import com.example.nasaapod.databinding.EarthFragmentBinding
import com.example.nasaapod.domain.EpicRepositoryImp
import com.example.nasaapod.ui.viewmodel.EpicViewModel
import kotlinx.coroutines.flow.collect

class EarthFargment: Fragment(R.layout.earth_fragment) {

    private lateinit var binding : EarthFragmentBinding

    private val epicViewModel : EpicViewModel by viewModels {
        EpicViewModel.EpicViewModelFactory(EpicRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            epicViewModel.requestEpic()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EarthFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.identifier.collect { id->
                id?.let {
                    binding.textFragEarth.text = id
                }

            }
        }

    }
}