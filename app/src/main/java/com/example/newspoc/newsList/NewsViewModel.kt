package com.example.newspoc.newsList

import android.util.Log
import androidx.lifecycle.*
import com.example.newspoc.NewsApiStatus
import com.example.newspoc.newsNetwork.Articles
import com.example.newspoc.newsNetwork.NewsApi
import com.example.newspoc.newsNetwork.NewsProperty
import kotlinx.coroutines.*

class NewsViewModel() : ViewModel() {
    private var viewModelJob=Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )


    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _response = MutableLiveData<NewsProperty>()
    val response : LiveData<NewsProperty>
        get() = _response

    private val _selectedNews = MutableLiveData<Articles>()
    val selectedNews : LiveData<Articles>
        get() = _selectedNews



    private  val _articleList = MutableLiveData<List<Articles>>()
    val articleList : LiveData<List<Articles>>
        get() = _articleList

    fun eventNavigateToNewsDetail(article: Articles){
        _selectedNews.value = article
    }
    fun eventNavigateToNewsDetailCompleted(){
        _selectedNews.value = null
    }
    init {
        _status.value = NewsApiStatus.LOADING
        getNews()
    }
        private fun getNews(){

            Log.i("getNews","Called")
            coroutineScope.launch {
                val deferred= NewsApi.retrofitService.getHealines("in")
                Log.i("NewsViewModel: ", "The Deferred instance is : ${deferred.toString()}")
                try{
                    _status.value = NewsApiStatus.LOADING
                    _response.value = deferred.await()
                    if(_response.value != null){
                        _articleList.value = _response.value!!.articles
                        _status.value = NewsApiStatus.DONE
                    }
                }catch (t : Throwable){
                    _status.value = NewsApiStatus.ERROR
                    _articleList.value = listOf()
                }
                Log.i("NewsViewModel: ", "The Article List is : ${_articleList.value.toString()}")
            }
        }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
