package com.example.newsarticles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsarticles.Models.Article
import com.example.newsarticles.R
import com.example.newsarticles.databinding.ItemArticlePreviewBinding
import com.example.newsarticles.ui.fragemnts.*


class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    private var differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {

        return NewsHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_article_preview,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsHolder, position: Int) {
        val article = diff.currentList[position]
        holder.itemView.apply {
            holder.title.text=article.title
            holder.description.text=article.description
            holder.puplisheAt.text=article.publishedAt
            holder.source.text=article.source?.name
             Glide.with(this).load(article.urlToImage).into(holder.image)
            //set on click
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }


        }

        }



    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

   fun setOnItemClickListener(Listener: (Article) -> Unit) {
        onItemClickListener = Listener

    }
    inner class NewsHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val image=itemView.findViewById<ImageView>(R.id.ivArticleImage)
        val title=itemView.findViewById<TextView>(R.id.tvTitle)
        val description=itemView.findViewById<TextView>(R.id.tvDescription)
        val puplisheAt=itemView.findViewById<TextView>(R.id.tvPublishedAt)
        val source=itemView.findViewById<TextView>(R.id.tvSource)
    }


}