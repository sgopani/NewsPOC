package com.example.newspoc.newsNetwork

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class NewsProperty (
    @SerializedName("status")
    val status : String,

    @SerializedName("totalResults")
    val totalResults : Long,

    @SerializedName("articles")
    val articles : List<Articles> = listOf()
) : Parcelable

