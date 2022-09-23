package com.example.animerecommender.network

import com.example.animerecommender.categoryImages.ImageCategory
import com.example.animerecommender.data.AnimeItem
import com.example.animerecommender.data.AnimeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


const val BASE_URL = "https://kitsu.io/api/edge/anime/"

val list = mutableListOf<AnimeItem>()

interface AnimeInterface {
    @GET("?page%5Blimit%5D=20")
     fun getAnimeData(): Call<AnimeResponse>

    @GET
    fun getAnimeByName(@Url url: String?): Call<AnimeResponse>

    @GET
    fun getUsers(@Url url: String?): Call<AnimeResponse>

    @GET
    fun getNextData(@Url url: String?): Call<AnimeResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object AnimeService {
    val animeInstance: AnimeInterface by lazy {
        retrofit.create((AnimeInterface::class.java))
    }
}
