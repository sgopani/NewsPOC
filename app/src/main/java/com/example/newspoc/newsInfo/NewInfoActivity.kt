package com.example.newspoc.newsInfo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newspoc.R
import com.example.newspoc.newsNetwork.Articles
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class NewInfoActivity : AppCompatActivity() {
    private lateinit var  articles: Articles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_info)
        //val article =NewsInfoFragmentArgs.fromBundle(requireArguments()).article
        articles = intent.extras!!.getParcelable("articles")!!
        val viewModelFactory = NewsInfoViewModelFactoryModelFactory(articles!!)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewsInfoViewModel::class.java)
        val newsImage=findViewById<ImageView>(R.id.news_info_image)
        val newsTitle=findViewById<TextView>(R.id.news_info_title)
        val newsDescription=findViewById<TextView>(R.id.news_info_description)
        val newsUrl=findViewById<TextView>(R.id.news_article_url)
        val author=findViewById<TextView>(R.id.author)
        val dateTv=findViewById<TextView>(R.id.date)
        author.text=articles.author
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date: String = sdf.format(Date())
        dateTv.text = date
        val saveImage=findViewById<Button>(R.id.saveImage)
        val shareIcon=findViewById<ImageView>(R.id.shareIcon)
        saveImage.setOnClickListener {
            val bm = (newsImage.drawable as BitmapDrawable).bitmap
            SaveImage(bm)
        }
        shareIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            //intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
            intent.putExtra(Intent.EXTRA_TEXT, articles.title + "/n" +articles.description)
            startActivity(Intent.createChooser(intent, null))
        }
        viewModel.selectedNews.observe(this, Observer {
            viewModel.getSetImage(newsImage)
            newsDescription.text = viewModel.newsDescription
            newsTitle.text=viewModel.newsTitle
            newsUrl.text = "Read More:\n${(viewModel.articleUrl)}"
        })


    }
    private fun SaveImage(finalBitmap: Bitmap) {
        val directoryToStore: File? = baseContext.getExternalFilesDir("NewApp")
        if (!directoryToStore!!.exists()) {
            directoryToStore.mkdir()
        }
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$directoryToStore/saved_images")
        myDir.mkdirs()
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fname = "Image-$n.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            val toast = Toast.makeText(this, "Image Saved successfully to your device", Toast.LENGTH_LONG)
            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}