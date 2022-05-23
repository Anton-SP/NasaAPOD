package com.example.nasaapod.ui.apod

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentPageApodBinding
import com.example.nasaapod.domain.NasaApodRepositoryImp
import com.example.nasaapod.ui.mars.MarsPageFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NasaApodPageFragment : Fragment(R.layout.fragment_page_apod) {

    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"

        fun instance(number: Int) = NasaApodPageFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }

    }

    private lateinit var binding: FragmentPageApodBinding

    private val apodViewModel: NasaApodViewModel by viewModels {
        NasaApodViewModel.NasaApodViewModelFactory(NasaApodRepositoryImp())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            apodViewModel.requestApod()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageApodBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            apodViewModel.loading.collect() {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            apodViewModel.error.collect() {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        arguments?.let {
            val id: Int = it.getInt(NasaApodPageFragment.ARG_NUMBER)
            Log.d("HAPPY", "id is = " + id)

            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                apodViewModel.loading.collect {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            }

            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                apodViewModel.error.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            when (id) {
                0 -> {
                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                        apodViewModel.today.collect() { day ->
                            day?.let {
                                binding.apodImg.load(day.url)
                                binding.textViewTitle.text = day.title
                                binding.textViewExplanation.text = day.explanation
                            }
                        }
                    }
                }
                1 -> {
                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                        apodViewModel.oneDayAgo.collect() { day ->
                            day?.let {
                                binding.apodImg.load(day.url)
                                binding.textViewTitle.text = day.title
                                binding.textViewExplanation.text = day.explanation
                            }
                        }
                    }
                }
                2 -> {
                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                        apodViewModel.twoDaysAgo.collect() { day ->
                            day?.let {
                                binding.apodImg.load(day.url)
                                binding.textViewTitle.text = day.title
                                binding.textViewExplanation.text = day.explanation
                            }
                        }
                    }
                }
                else -> {
                    Toast.makeText(requireContext(), "ERROR TAB!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}





