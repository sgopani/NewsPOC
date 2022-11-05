package com.example.newspoc.newsInfo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newspoc.R
import com.example.newspoc.newsNetwork.Articles

class NewInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_info)
        //val article =NewsInfoFragmentArgs.fromBundle(requireArguments()).article
        val articles: Articles? = intent.extras!!.getParcelable("articles")
        val viewModelFactory = NewsInfoViewModelFactoryModelFactory(articles!!)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewsInfoViewModel::class.java)
        val newsImage=findViewById<ImageView>(R.id.news_info_image)
        val newsTitle=findViewById<TextView>(R.id.news_info_title)
        val newsDescription=findViewById<TextView>(R.id.news_info_description)
        val newsUrl=findViewById<TextView>(R.id.news_article_url)

        viewModel.selectedNews.observe(this, Observer {
            viewModel.getSetImage(newsImage)
            newsDescription.text = viewModel.newsDescription
            newsTitle.text=viewModel.newsTitle
            newsUrl.text = "Read More:\n${(viewModel.articleUrl)}"
        })
    }
}