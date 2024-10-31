package com.example.hugeproj.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hugeproj.R
import com.example.hugeproj.model.Article
import com.squareup.picasso.Picasso

class CovidNewsAdapter(private var CovidNewsList: List<Article>, private val onItemClickListener: (String) -> Unit) : RecyclerView.Adapter<CovidNewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidNewsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val news_row = layoutInflater.inflate(R.layout.news_row,parent,false)
        return CovidNewsHolder(news_row)
    }
    override fun onBindViewHolder(holder: CovidNewsHolder, position: Int) {
        val covidNews = CovidNewsList[position]

        Picasso.get()
            .load(covidNews.media)  // Assuming 'media' is the URL of the image
            .into(holder.img_media)

        holder.tv_title.text = covidNews.title
        holder.tv_author.text = "Author: " + covidNews.author
        holder.itemView.setOnClickListener{
            onItemClickListener(covidNews.link)
        }
    }

    override fun getItemCount(): Int {
        return CovidNewsList.size
    }

    fun updateNews(newNewsList: List<Article>) {
        this.CovidNewsList = newNewsList
        notifyDataSetChanged()
    }

}

class CovidNewsHolder(view : View) : RecyclerView.ViewHolder(view){
    val img_media: ImageView = view.findViewById(R.id.img_media)
    val tv_title : TextView = view.findViewById(R.id.tv_title)
    val tv_author : TextView = view.findViewById(R.id.tv_author)
}