package com.example.nasaapod.ui.earth

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.nasaapod.R

class EarthPageFragment : Fragment(R.layout.fragment_page_earth) {

    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"

        fun instance(number: Int) = EarthPageFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(EpicViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.epicList.collect { epicList ->

                epicList.let {
                    val id = arguments?.getInt(ARG_NUMBER)?.toInt()
                    view.findViewById<TextView>(R.id.epic_test).text = epicList[id!!]?.image ?: "000"
                }

            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.identifier.collect { id ->
                id?.let {
                    //    binding.textFragEarth.text = id
                }

            }
        }
    }
}