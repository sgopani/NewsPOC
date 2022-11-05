package com.example.newspoc

import com.example.newspoc.newsNetwork.Articles

interface NewsItemClickListener {
    fun onNewsItemClick(article: Articles)
}