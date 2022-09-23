package com.example.animerecommender.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryGrid(
    val imageUrl: String?,
    val category: String?,
) : Parcelable