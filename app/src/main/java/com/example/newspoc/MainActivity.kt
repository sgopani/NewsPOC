package com.example.newspoc

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspoc.newsInfo.NewInfoActivity
import com.example.newspoc.newsList.NewsAdapter
import com.example.newspoc.newsList.NewsViewModel
import com.example.newspoc.newsNetwork.Articles
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var retryButton: Button
    private lateinit var statusImageView: ImageView
    private lateinit var progress: ProgressBar
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var newsListView: View
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_list)
        val adapter = NewsAdapter(getNewsItemClickListener())
        newsListView=findViewById(R.id.newsList)
        newsList.adapter=adapter
        viewModel= NewsViewModel()
        statusImageView=findViewById(R.id.status_image)
        progress=findViewById(R.id.news_progres)
        retryButton= findViewById(R.id.retry_button)
        gridLayoutManager= GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        newsList.layoutManager=gridLayoutManager
        newsList.setHasFixedSize(true)

        viewModel.articleList.observe(this, Observer { it ->
            it?.let {
                //Log.i("Viemodel article total", "${viewModel.articleList.value!!}")
                //Toast.makeText(this.requireContext(),"${viewModel.articleList.value!!}",Toast.LENGTH_LONG).show()
                adapter.submitList(viewModel.articleList.value)

            }
        })

        viewModel.selectedNews.observe(this, Observer { article ->
            if (article != null) {
                val intent = Intent(this, NewInfoActivity::class.java)
                intent.putExtra("articles", article)
                startActivity(intent)
//                navController.navigate(NewsListDirections.actionNewsList2ToNewsInfoFragment2(article))
//                NewsListDirections.actionNewsList2ToNewsInfoFragment2(article)
//                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })

        viewModel.status.observe(this, Observer { status ->
            checkInternet(status)
        })
//        val fm: FragmentManager = supportFragmentManager
//        val fragment = NewsList()
//        fm.beginTransaction().add(R.id.main_container, fragment).commit()
    }
    //private var _binding: FragmentNewsListBinding? = null
    //private lateinit var factory: NewsViewModelFactory

    private fun checkInternet(status: NewsApiStatus) {

        progress.visibility= View.VISIBLE
        when (status) {
            NewsApiStatus.LOADING -> {
                //Toast.makeText(this.context,"Called",Toast.LENGTH_LONG).show()
                newsList.visibility= View.VISIBLE
                statusImageView.setImageResource(R.drawable.progress_animation)
            }
            NewsApiStatus.ERROR -> {
                retryButton.visibility= View.VISIBLE
                retryButton.setOnClickListener {
                    if (isInternetOn(this)){
                        Toast.makeText(this, "Connected to internet", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                }
                progress.visibility= View.GONE
                Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show()
                statusImageView.visibility= View.VISIBLE
                newsList.visibility= View.INVISIBLE
            }
            NewsApiStatus.DONE -> {
                progress.visibility= View.GONE
                statusImageView.visibility= View.GONE
                newsList.visibility= View.VISIBLE
            }
        }
    }
    @Suppress("DEPRECATION")
    private fun isInternetOn(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }


    //
    private fun getNewsItemClickListener(): NewsItemClickListener {
        return object : NewsItemClickListener {
            override fun onNewsItemClick(article: Articles) {
                viewModel.eventNavigateToNewsDetail(article)
            }


        }

    }
}