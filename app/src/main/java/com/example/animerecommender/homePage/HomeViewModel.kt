package com.example.animerecommender.homePage

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animerecommender.categoryImages.ImageCategory
import com.example.animerecommender.data.AnimeItem
import com.example.animerecommender.data.AnimeResponse
import com.example.animerecommender.network.AnimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    private val TAG = "HomeViewModel"

    // TODO: Implement the ViewModel
    private val _status = MutableLiveData<List<AnimeItem>>()
    var userLiveData: LiveData<List<AnimeItem>>? = _status
    val list = mutableListOf<AnimeItem>()
//    val slideItems = mutableListOf<SlideItem>()

    init {
        getAnimes()
    }


    fun getAnimes(): LiveData<List<AnimeItem>>? {
        val animes: Call<AnimeResponse> = AnimeService.animeInstance.getAnimeData()

        animes.enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(
                call: Call<AnimeResponse>,
                response: Response<AnimeResponse>,
            ) {
                val body = response.body()
                val items = body?.data
                for (i in 0..(items?.size?.minus(1) ?: 19)) {
                    var title = items?.get(i)?.attributes?.titles?.en_jp
                    if(title==null){
                        title = items?.get(i)?.attributes?.titles?.en_us
                    }
                    val year = items?.get(i)?.attributes?.createdAt
                    val epCount = items?.get(i)?.attributes?.episodeCount
                    val animeDesc = items?.get(i)?.attributes?.description
                    val videoId = items?.get(i)?.attributes?.youtubeVideoId
                    val imgUrl = items?.get(i)?.attributes?.posterImage?.small
                    val ratings = items?.get(i)?.attributes?.averageRating
                    val startedAt = items?.get(i)?.attributes?.startDate
                    val endedAt = items?.get(i)?.attributes?.endDate
                    val ageRating = items?.get(i)?.attributes?.ageRatingGuide
                    val item = AnimeItem(title,year,epCount,animeDesc,videoId,imgUrl,ratings,startedAt,endedAt,ageRating)
                    list.add(item)

                }
                _status.value = list

            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
            }
        })
        return userLiveData
    }



}


