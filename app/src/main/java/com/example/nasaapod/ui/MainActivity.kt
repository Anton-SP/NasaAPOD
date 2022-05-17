package com.example.nasaapod.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapod.R
import com.example.nasaapod.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val KEY_THEME = "KEY_THEME"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val theme = getPreferences(Context.MODE_PRIVATE).getInt(KEY_THEME, -1)
        //setTheme(theme)

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

        bottomNavigation.setOnItemSelectedListener { menuItem->
            when (menuItem.itemId) {
                R.id.apod_menu -> {
                    Log.d("HAPPY","apod")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, NasaApodFragment(), "MAIN")
                        .commitNow()
                    true
                }
                R.id.earth_menu -> {
                    Log.d("HAPPY","earth")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, EarthFargment(), "EARTH")
                        .commitNow()
                    true
                }
                R.id.mars_menu -> {
                    true
                }
                else -> false
            }
        }
/*
        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.apod_menu -> NasaApodFragment()
                R.id.earth_menu -> EarthFargment()
                //  R.id.action_tab -> TabHostFragment()
                else -> null
            }?.also { fragment ->
                Log.d("HAPPY","WOW")
                Toast.makeText(this,"HEHEHEH EARTH", Toast.LENGTH_SHORT).show()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit()
            }
           true
        }
        */

    }


}      /* when (menuItem.itemId) {
                R.id.apod_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, NasaApodFragment(), "MAIN")
                        .commitNow()
                    true
                }
                R.id.earth_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, EarthFargment(), "EARTH")
                        .commitNow()
                    true
                }
                R.id.mars_menu -> {
                    true
                }
                else -> false
            }
*/




