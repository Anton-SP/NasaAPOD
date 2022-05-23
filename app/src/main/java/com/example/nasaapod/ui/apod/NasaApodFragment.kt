package com.example.nasaapod.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.databinding.NasaApodFragmentBinding
import com.example.nasaapod.domain.NasaApodRepositoryImp
import com.example.nasaapod.ui.mars.MarsPageFragment

class NasaApodFragment : Fragment(R.layout.nasa_apod_fragment) {

    private lateinit var binding: NasaApodFragmentBinding

    private val viewModel: NasaApodViewModel by viewModels {
        NasaApodViewModel.NasaApodViewModelFactory(NasaApodRepositoryImp())
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

        binding.apodViewPager.adapter = NasaApodPagerAdapter(this)
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

    class NasaApodPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment = NasaApodPageFragment.instance(position)

    }


}