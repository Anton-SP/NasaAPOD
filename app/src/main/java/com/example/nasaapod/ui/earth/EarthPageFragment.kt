package com.example.nasaapod.ui.earth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentPageEarthBinding
import com.example.nasaapod.domain.EpicRepositoryImp

class EarthPageFragment : Fragment(R.layout.fragment_page_earth) {

    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"

        fun instance(number: Int) = EarthPageFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }
    }

    private lateinit var binding: FragmentPageEarthBinding

    private val epicViewModel:EpicViewModel by viewModels {
        EpicViewModel.EpicViewModelFactory(EpicRepositoryImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageEarthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // view.findViewById<TextView>(R.id.epic_test).text = arguments?.getInt(ARG_NUMBER)?.toString()
      //  binding.epicTest.text = arguments?.getInt(ARG_NUMBER)?.toString()
        arguments?.let {
            val id:Int = it.getInt(ARG_NUMBER)
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.epicList.collect { list ->
                list.let{
                    binding.epicTest.text = it.get(0)!!.date


              //     view.findViewById<TextView>(R.id.epic_test).text = arguments?.getInt(ARG_NUMBER)?.toString()
                 //   val id = arguments?.getInt(ARG_NUMBER)
             //       view.findViewById<TextView>(R.id.epic_test).text = list[2]?.image
                }

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.error.collect {

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel.identifier.collect { id ->
                id?.let {

                }

            }
        }
    }
}