package com.example.nasaapod.ui

import android.content.Context
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapod.R
import com.example.nasaapod.databinding.ActivityMainBinding
import com.example.nasaapod.ui.apod.NasaApodFragment
import com.example.nasaapod.ui.earth.EarthFargment
import com.example.nasaapod.ui.mars.MarsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val KEY_THEME = "KEY_THEME"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val theme = getPreferences(Context.MODE_PRIVATE).getInt(KEY_THEME, -1)
        setTheme(theme)

        super.onCreate(savedInstanceState)
       /* binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root*/
      //  setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, NasaApodFragment())
                .commit()
        }

       val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_nav_bar)

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.apod_menu -> NasaApodFragment()
                R.id.earth_menu -> EarthFargment()
                R.id.mars_menu -> MarsFragment()
                else -> null
            }?.also { fragment ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment,"EPIC")
                    .commit()
            }
            true
        }

    }


}




