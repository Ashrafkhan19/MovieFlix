package com.emir.movieflix.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.emir.movieflix.R
import com.emir.movieflix.databinding.ActivityMainBinding
import com.emir.movieflix.fragment.NowPlayingFragment
import com.emir.movieflix.fragment.TopRatedFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        openHome()

        binding.bottomNaviBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_now_playing -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, NowPlayingFragment())
                        .commit()

                    supportActionBar?.title = "Now Playing"
                    Toast.makeText(this, "Now playing", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.ic_top_rated -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, TopRatedFragment())
                        .commit()

                    supportActionBar?.title = "Top Rated"
                    Toast.makeText(this, "Top rated", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnItemSelectedListener true

        }


    }

    private fun openHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, NowPlayingFragment())
            .commit()

        supportActionBar?.title = "Home"
    }
}
