package com.emir.movieflix.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadFromUrl")
fun ImageView.loadFromUrl(url: String){
    Glide.with(this).load("https://image.tmdb.org/t/p/w342/$url")
        .into(this)
}