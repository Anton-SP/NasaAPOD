package com.example.nasaapod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NasaApodFragmentBinding
import com.example.nasaapod.domain.NasaApodRepositoryImp
import com.example.nasaapod.ui.viewmodel.NasaApodViewModelFactory
import com.example.nasaapod.ui.viewmodel.NasaApodViewModel

class NasaApodFragment : Fragment(R.layout.nasa_apod_fragment) {

    private lateinit var binding: NasaApodFragmentBinding

    private val viewModel: NasaApodViewModel by viewModels {
        NasaApodViewModelFactory(NasaApodRepositoryImp())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.requestApod()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NasaApodFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect { url ->
                url?.let {
                    binding.apodImg.load(it)
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.title.collect { title ->
                title?.let {
                   binding.textViewTitle.text = title
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.date.collect { date ->
                binding.chipToday.apply {
                    setOnCheckedChangeListener(null)
                    isChecked = true
                    setOnCheckedChangeListener { compoundButton, isChecked -> }
                }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.explanation.collect { info ->
                info?.let {
                    binding.textViewExplanation.text = info
                }
            }
        }
/*
        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu -> {
                    val modalBottonSheet = wikiSearchBottom()
                    modalBottonSheet.show(requireActivity().supportFragmentManager, "modal_tag")
                    true
                }
                R.id.theme_space -> {
                    savedTheme = R.style.Theme_NasaAPOD
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    with(sharedPref!!.edit()) {
                        putInt(MainActivity.KEY_THEME, savedTheme!!)
                        apply()
                    }
                    requireActivity().recreate()
                    true
                }

                R.id.theme_alternative -> {
                    savedTheme = R.style.Theme_NasaAPOD_alternative
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    with(sharedPref!!.edit()) {
                        putInt(MainActivity.KEY_THEME, savedTheme!!)
                        apply()
                    }
                    requireActivity().recreate()
                    true
                }

                else -> false
            }


        }
    */
    }

}