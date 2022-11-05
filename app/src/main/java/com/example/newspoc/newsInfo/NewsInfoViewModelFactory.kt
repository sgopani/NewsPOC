package com.example.newspoc.newsInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newspoc.newsNetwork.Articles

@Suppress("UNCHECKED_CAST")
class NewsInfoViewModelFactoryModelFactory(private val article: Articles) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsInfoViewModel::class.java)){
            return NewsInfoViewModel(article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
