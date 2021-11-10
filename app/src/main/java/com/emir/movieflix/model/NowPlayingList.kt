package com.emir.movieflix.model

data class NowPlayingList(
    val dates: Dates? = null,
    val page: Int,
    val results: MutableList<Result>,
    val total_pages: Int,
    val total_results: Int
)