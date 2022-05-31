package com.example.nasaapod.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasaapod.R
import com.example.nasaapod.databinding.FragmentSettingsBinding
import com.example.nasaapod.ui.MainActivity.Companion.KEY_THEME

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding : FragmentSettingsBinding

    private var savedTheme:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeSpaceButton.setOnClickListener {
            savedTheme = R.style.Theme_NasaAPOD
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref!!.edit()) {
                putInt(KEY_THEME, savedTheme!!)
                apply()
            }
            requireActivity().recreate()
        }

        binding.themeAlterSpaceButton.setOnClickListener {
            savedTheme = R.style.Theme_NasaAPOD_alternative
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref!!.edit()) {
                putInt(KEY_THEME, savedTheme!!)
                apply()
            }
            requireActivity().recreate()
        }


    }
}