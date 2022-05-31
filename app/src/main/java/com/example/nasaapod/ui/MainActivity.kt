package com.example.nasaapod.ui

import android.content.Context
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.nasaapod.R
import com.example.nasaapod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val KEY_THEME = "KEY_THEME"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val theme = getPreferences(Context.MODE_PRIVATE).getInt(KEY_THEME, -1)
        setTheme(theme)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, MainFragment(), "MAIN")
                .commitNow()
        }
    }

}

