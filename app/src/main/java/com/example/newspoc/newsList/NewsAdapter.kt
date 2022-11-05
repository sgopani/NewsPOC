package com.example.newspoc.newsList
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newspoc.DiffCallback
import com.example.newspoc.NewsItemClickListener
import com.example.newspoc.R
import com.example.newspoc.loadImage
import com.example.newspoc.newsNetwork.Articles


class NewsAdapter(private val newsItemClickListeners: NewsItemClickListener) :
    ListAdapter<Articles, NewsAdapter.MyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.createViewHolder(parent)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articlesadapter=getItem(position)
        holder.bind(articlesadapter)
        holder.newsTitle.text=articlesadapter!!.title
        holder.itemView.setOnClickListener {
            newsItemClickListeners.onNewsItemClick(articlesadapter)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle: TextView=itemView.findViewById(R.id.newsTitle)
        var image: ImageView=itemView.findViewById(R.id.newsImage)
        var favImage:CheckBox=itemView.findViewById(R.id.fav_CheckBox)
        companion object {

            fun createViewHolder(parent: ViewGroup): MyViewHolder {
                val newsView=LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_list_degsign, parent, false)

                return MyViewHolder(newsView)
            }
        }
        fun bind(article: Articles) {
            article.let { it ->

                newsTitle.text=it.title
                var imgUri: Uri?=null
                it.imageUrl?.let {
                    imgUri=it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    image.setImageResource(R.drawable.try_later)
                }
                else  {
                    imgUri?.let { uri ->
                        loadImage(uri, image)
                    }
                }
            }
        }
    }
}




