package com.emir.movieflix.api

import com.emir.movieflix.model.NowPlayingList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TmdbApi {
    @GET("now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
    fun nowPlayingList(): Call<NowPlayingList>

    @GET("top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
    fun topRatedList(): Call<NowPlayingList>



    companion object {

        //https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
        //https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"


        fun create(): TmdbApi{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(TmdbApi::class.java)
        }
    }
}