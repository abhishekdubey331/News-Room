package com.doubtnut.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.core.base.extensions.timeFormatForPublished
import com.core.base.utils.ImageHelper
import com.doubtnut.assignment.R
import com.doubtnut.assignment.commons.data.NewsArticle
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_list_row.*


class NewsListAdapter(
        private val newsList: List<NewsArticle?>?,
        private val context: Context, private val imageHelper: ImageHelper, private val onClick: (NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList?.let {
            holder.bindData(it[position], context, imageHelper)
        }
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.news_list_row, parent, false).let {
                    ViewHolder(it, onClick)
                }
    }

    class ViewHolder(override val containerView: View, private val onClick: (NewsArticle) -> Unit) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(newsStory: NewsArticle?, context: Context, imageHelper: ImageHelper) {
            newsStory?.let {
                with(newsStory) {
                    newsTitle.text = title
                    newsAuthor.text = author
                    newsPublishedAt.text = publishedAt?.timeFormatForPublished()
                    urlToImage?.let {
                        imageHelper.loadImage(context, urlToImage, newsImage)
                    }
                    containerView.setOnClickListener { onClick(this) }
                }
            }
        }
    }

}