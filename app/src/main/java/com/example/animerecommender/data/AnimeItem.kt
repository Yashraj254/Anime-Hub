package com.example.animerecommender.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeItem(
    val animeTitle: String?,
    val createdAt: String?,
    val epCount: Int?,
    val animeDesc: String?,
    val videoId: String?,
    val imgUrl: String?,
    val avgRating: String?,
    val startedAt: String?,
    val endedAt: String?,
    val ratingGuide: String?
) : Parcelable
