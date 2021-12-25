package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.newsapp3.R

import kotlinx.android.synthetic.main.article_preview.view.*
import models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//DiffUtil is a utility class that calculates the difference between two lists and outputs a
// list of update operations that converts the first list into the second one. It can be used
// to calculate updates for a RecyclerView Adapter
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        //in this we check whether the articles or the items are same or not
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        //in this we check whether their contents are same or not
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
// Glide is an Image Loader Library for Android developed by bumptech and is a
// library that is recommended by Google. It has been used in many Google open source
// projects including Google I/O 2014 official application. It provides animated GIF support and handles image loading/caching.
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(article) }//the let function is very similar to following code
                //if(onItemClickListener!=null){
                //      onItemClickListener(article)}
                //here it means the lambda function and article is from above
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null//this represents that it is a lambda function which takes article as
    //parameter and returns unit

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        //when a item is clicked we define the onclicklistener as below
        onItemClickListener = listener
    }
}