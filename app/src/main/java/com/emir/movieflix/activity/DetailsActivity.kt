package com.emir.movieflix.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.emir.movieflix.R
import com.emir.movieflix.databinding.ActivityDetailsBinding
import java.text.SimpleDateFormat

const val TAG = "Details"
class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        if(intent != null) {
            val title = intent.getStringExtra("title")
            val overview = intent.getStringExtra("overview")
            val path = intent.getStringExtra("path")
            val date = intent.getStringExtra("date")
            val vote = intent.getStringExtra("v")

            /*val formatter = SimpleDateFormat("MMMM dd, yyyy")
            val d = formatter.format(date)*/

            binding.title = title
            binding.overview = overview
            binding.path = path
            binding.date = date.toString()
            binding.vote = vote

            Log.d(TAG, "onCreate: $date")
            Log.d(TAG, "onCreate: $vote")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}