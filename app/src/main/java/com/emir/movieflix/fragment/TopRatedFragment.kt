package com.emir.movieflix.fragment

import android.annotation.SuppressLint
import android.os.Bundle
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "TopRatedFragment"

class TopRatedFragment : Fragment() {
    lateinit var binding: FragmentNowPlayingBinding
    lateinit var manager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)
        
        val topRated = TmdbApi.create().topRatedList()

        context?.let {
            topRated.enqueue(object : Callback<NowPlayingList> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<NowPlayingList>, response: Response<NowPlayingList>) {
                    val topRatedList = response.body()

                    if (topRatedList != null) {
                        binding.progressBar.visibility = View.GONE
                        val topRatedDisplayList = NowPlayingList(
                            null,
                            3,
                            topRatedList.results,
                            3,5
                        )
                        Log.d(TAG, "onResponse: ${topRatedList.results}")
                        manager = LinearLayoutManager(context)
                        binding.rvNowPlaying.apply {
                            adapter = NowPlayingRVAdapter(topRatedDisplayList)
                            layoutManager = manager
                            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        }

                        binding.swipeRefLayout.setOnRefreshListener {
                            binding.rvNowPlaying.adapter?.notifyDataSetChanged()
                            binding.swipeRefLayout.isRefreshing = false
                        }

                        binding.searchMovie.setOnQueryTextListener(object :
                            SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                /* binding.searchMovie.clearFocus()
                                 nowPlayingList.results.forEach {
                                     if(query?.let { it1 -> it.title.contains(it1, true) } == true){

                                     }
                                 }*/
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                Log.d(TAG, "onQueryTextChange: ${newText.toString()}")
                                //topRatedDisplayList.results.clear()
                                val lit = mutableListOf<Result>()
                                topRatedList.results.forEach {
                                    if (it.title.lowercase().contains(newText?.lowercase().toString())) {
                                        Log.d(TAG, "onQIt: $it")
                                        val list = it
                                        Log.d(TAG, "nowPlayingListDisplay:${list} ")

                                        lit.add(it)

                                        //nowPlayingDisplayList.results.add(it)

                                    }
                                }
                                binding.rvNowPlaying.adapter = NowPlayingRVAdapter(
                                    NowPlayingList(
                                        null,
                                        3,
                                        lit,
                                        4,5
                                    )
                                )
                                //binding.rvNowPlaying.adapter?.notifyDataSetChanged()

                                return true
                            }

                        })


                        val swipeGesture = object : SwipeGesture(it) {
                            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                                when (direction) {
                                    ItemTouchHelper.LEFT -> {
                                        binding.rvNowPlaying.adapter?.apply {
                                            topRatedDisplayList.results.removeAt(viewHolder.adapterPosition)
                                            notifyItemRemoved(viewHolder.adapterPosition)
                                        }

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
        
        return binding.root
    }

}