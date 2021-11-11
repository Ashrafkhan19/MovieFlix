package com.emir.movieflix.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emir.movieflix.R
import com.emir.movieflix.activity.DetailsActivity
import com.emir.movieflix.databinding.RvNowPlayingSingleRowBinding
import com.emir.movieflix.fragment.TAG
import com.emir.movieflix.model.NowPlayingList
import com.emir.movieflix.model.Result

class NowPlayingRVAdapter(private val nowPlayingList: NowPlayingList): RecyclerView.Adapter<NowPlayingRVAdapter.NowPlayingViewHolder>() {

    class NowPlayingViewHolder(val context: Context? = null, private val binding: RvNowPlayingSingleRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.movieTitle = result
            binding.movieDesc = result

            binding.liMovie.setOnClickListener {
                val intent = Intent(it.context, DetailsActivity::class.java)
                intent.putExtra("title", result.original_title)
                intent.putExtra("overview", result.overview)
                intent.putExtra("path", result.poster_path)
                intent.putExtra("date", result.release_date)
                intent.putExtra("v", result.vote_average.toString())
                Log.d(TAG, "bind: ${result.vote_average.toString()}")
                it.context.startActivity(intent)
                (it.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvNowPlayingSingleRowBinding.inflate(inflater, parent, false)
        return NowPlayingViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.bind(nowPlayingList.results[position])
    }

    override fun getItemCount() = nowPlayingList.results.size


}