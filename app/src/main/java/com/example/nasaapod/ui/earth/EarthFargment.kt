package com.example.nasaapod.ui.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.nasaapod.R
import com.example.nasaapod.databinding.EarthFragmentBinding
import com.example.nasaapod.domain.EpicRepositoryImp
import kotlinx.coroutines.flow.collect

class EarthFargment: Fragment(R.layout.earth_fragment) {

    private lateinit var binding : EarthFragmentBinding


    private val epicViewModel by lazy {
        ViewModelProvider(requireActivity()).get(EpicViewModel::class.java)
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
        var length = 0

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            epicViewModel._epicList.collect{list->
                list?.let{
                    length = list.size
                    binding.earthViewPager.adapter = PagerAdapter(requireParentFragment())
                }

            }

        }




    }


    class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


        override fun getItemCount(): Int = 10

        override fun createFragment(position: Int): Fragment = EarthPageFragment.instance(position)

    }

}