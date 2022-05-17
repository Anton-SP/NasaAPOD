package com.example.nasaapod.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasaapod.R
import com.example.nasaapod.databinding.EarthFragmentBinding

class EarthFargment: Fragment(R.layout.earth_fragment) {

    private lateinit var binding : EarthFragmentBinding

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
   //     Toast.makeText(requireContext(),"HEHEHEH EARTH",Toast.LENGTH_SHORT).show()
    }
}