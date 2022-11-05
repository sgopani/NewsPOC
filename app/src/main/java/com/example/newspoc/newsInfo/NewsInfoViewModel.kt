package com.example.newspoc.newsInfo
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newspoc.R
import com.example.newspoc.newsNetwork.Articles

class NewsInfoViewModel (var articles: Articles): ViewModel(){
    private val _selectedNews = MutableLiveData<Articles>()
    val selectedNews : LiveData<Articles>
        get() = _selectedNews
    init {
        _selectedNews.value=articles
    }
    private val imageUrl = _selectedNews.value?.imageUrl

    val newsDescription = _selectedNews.value?.description

    val articleUrl= _selectedNews.value?.imageUrl
    val newsTitle=_selectedNews.value?.title
    fun getSetImage(newsImage:ImageView){
        var imgUri: Uri? = null

        imageUrl?.let {
            imgUri =it.toUri().buildUpon().scheme("https").build()
        }

        if (imgUri == null) {
            newsImage.visibility = View.GONE
        } else {
            Glide.with(newsImage.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.try_later)
                )
                .into(newsImage)
        }

    }
}