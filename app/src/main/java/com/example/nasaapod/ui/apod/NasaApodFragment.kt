package com.example.nasaapod.ui.apod

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NasaApodFragmentBinding
import com.example.nasaapod.domain.NasaApodRepositoryImp
import com.example.nasaapod.ui.mars.MarsFragment
import com.example.nasaapod.ui.mars.MarsPageFragment
import com.example.nasaapod.ui.transforms.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate

class NasaApodFragment : Fragment(R.layout.nasa_apod_fragment) {




    private lateinit var binding: NasaApodFragmentBinding

    private val viewModel: NasaApodViewModel by viewModels {
        NasaApodViewModel.NasaApodViewModelFactory(NasaApodRepositoryImp())
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        binding = NasaApodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = this

        binding.apodViewPager.setPageTransformer(ZoomOutPageTransformer())

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.today.collect() { day ->
                binding.apodViewPager.adapter = NasaApodPagerAdapter(fragment)
                TabLayoutMediator(binding.apodTabs, binding.apodViewPager) { tab, position ->

                    when (position) {
                        0 -> tab.text = day.date
                        1 -> tab.text = LocalDate.now().minusDays(1).toString()
                        2 -> tab.text = LocalDate.now().minusDays(2).toString()
                    }

                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_apod
                    )
                }.attach()
            }
        }

    }

    class NasaApodPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment =
            NasaApodPageFragment.instance(position)
    }



}


/*
class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}




*/
