package com.doubtnut.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.core.base.extensions.timeFormatForPublished
import com.doubtnut.assignment.R
import com.doubtnut.assignment.commons.data.NewsArticle
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_list_row.*


class NewsListAdapter(
        private val newsList: List<NewsArticle?>?,
        private val context: Context
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList?.let {
            holder.bindData(it[position], context)
        }
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.news_list_row, parent, false).let {
                    ViewHolder(it)
                }
    }

    class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(newsStory: NewsArticle?, context: Context) {
            newsStory?.let {
                with(newsStory) {
                    newsTitle.text = title
                    newsAuthor.text = author
                    newsPublishedAt.text = publishedAt?.timeFormatForPublished()
                    Glide.with(context)
                            .load(urlToImage)
                            .error(R.drawable.tools_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(newsImage)
                }
            }
        }
    }

}