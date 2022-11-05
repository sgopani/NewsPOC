package com.example.newspoc.newsNetwork

import com.example.newspoc.apiKey
import com.example.newspoc.newsNetwork.NewsProperty
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=in&apiKey=d29d58aab88d4ea0b04ddb245a230068

interface NewsApiService {
    @GET("top-headlines?apiKey=$apiKey")
    fun getHealines(@Query("country") country : String): Deferred<NewsProperty>

}
