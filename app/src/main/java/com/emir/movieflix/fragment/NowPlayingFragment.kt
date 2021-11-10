package com.emir.movieflix.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emir.movieflix.R
import com.emir.movieflix.adapter.NowPlayingRVAdapter
import com.emir.movieflix.adapter.SwipeGesture
import com.emir.movieflix.api.TmdbApi
import com.emir.movieflix.databinding.FragmentNowPlayingBinding
import com.emir.movieflix.model.NowPlayingList
import com.emir.movieflix.model.Result
import com.emir.movieflix.util.ConnectionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NowPlayingFragment : Fragment() {


    //Now Playing Variable
    lateinit var binding: FragmentNowPlayingBinding
    val TAG = "Now Playing Fragment"
    lateinit var manager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)

        val nowplaying = TmdbApi.create().nowPlayingList()

        if(ConnectionManager().checkConnectivity(requireContext())){
            context?.let {
                nowplaying.enqueue(object : Callback<NowPlayingList> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(
                        call: Call<NowPlayingList>,
                        response: Response<NowPlayingList>
                    ) {
                        val nowPlayingList = response.body()
                        if (nowPlayingList != null) {
                            binding.progressBar.visibility = View.GONE
                            Log.d(TAG, "onResponse: ${nowPlayingList.total_results}")
                            manager = LinearLayoutManager(context)
                            binding.rvNowPlaying.apply {
                                adapter = NowPlayingRVAdapter(nowPlayingList)
                                layoutManager = manager
                                addItemDecoration(
                                    DividerItemDecoration(
                                        context,
                                        LinearLayoutManager.VERTICAL
                                    )
                                )
                            }
                            /*binding.searchMovie.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                                    override fun onQueryTextSubmit(query: String?): Boolean {
                                        binding.searchMovie.clearFocus()
                                        nowPlayingList.results.forEach {
                                            if(query?.let { it1 -> it.title.contains(it1, true) } == true){
                                                binding.rvNowPlaying.adapter.filter.filter(query)
                                            }
                                        }
                                        return false
                                    }

                                    override fun onQueryTextChange(newText: String?): Boolean {
                                        TODO("Not yet implemented")
                                    }

                                })*/

                            binding.swipeRefLayout.setOnRefreshListener {
                                binding.rvNowPlaying.adapter?.notifyDataSetChanged()
                                binding.swipeRefLayout.isRefreshing = false
                            }

                            val swipeGesture = object : SwipeGesture(it) {
                                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                                    when (direction) {
                                        ItemTouchHelper.LEFT -> {
                                            binding.rvNowPlaying.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                                        }
                                    }
                                }
                            }
                            val touchHelper = ItemTouchHelper(swipeGesture)
                            touchHelper.attachToRecyclerView(binding.rvNowPlaying)
                        }
                    }

                    override fun onFailure(call: Call<NowPlayingList>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }

                })
            }
        } else {
            // Internet maujud nahi hai
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("No Internet Connection")
            dialog.setPositiveButton("Settings") { _, _ ->
                //Settings khole
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { a,b ->
                activity?.finishAffinity()
            }
            dialog.create()
            dialog.show()
        }



        return binding.root
    }


}