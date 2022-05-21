package com.example.nasaapod.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentPageMarsBinding
import com.example.nasaapod.domain.MarsRepositoryImp

class MarsPageFragment : Fragment(R.layout.fragment_page_mars) {
    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"

        fun instance(number: Int) = MarsPageFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }
    }

    private lateinit var binding: FragmentPageMarsBinding

    private val marsViewModel:MarsViewModel by viewModels {
        MarsViewModel.MarsViewModelFactory(MarsRepositoryImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageMarsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id:Int = it.getInt(ARG_NUMBER)
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            marsViewModel.images.collect{ images ->
                images.let {
                    binding.marsPhoto.load(it[id].imgSrc)
                }
            }
        }
    }

}