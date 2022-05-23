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
        binding = FragmentPageApodBinding.inflate(inflater,container,false)
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
            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
       apodViewModel.date.collect(){ date ->
           date?.let {
               var today : LocalDate = LocalDate.now()
               var yesterday  : LocalDate = today.minusDays(1)
               var yesterday2  : LocalDate = today.minusDays(2)
               /*
               var dateFormat = SimpleDateFormat("yyyy-mm-dd")
               var str = dateFormat.format(it)*/
               Log.d("HAPPY",today.toString())
               Log.d("HAPPY",yesterday.toString())
               Log.d("HAPPY",yesterday2.toString())
           }



          /*   //   marsViewModel.photos.collect() { photos ->
                    photos.let {
                        binding.marsPhoto.load(photos.latestPhotos[id].imgSrc)
                        binding.dateImage.text = photos.latestPhotos[id].earthDate
                    }*/
                }
            }
        }

    }
}
/*
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


*/




