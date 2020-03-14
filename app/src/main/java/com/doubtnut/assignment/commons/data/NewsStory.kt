package com.doubtnut.assignment.commons.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class NewsStory(
        @SerializedName("articles")
        val newsArticles: List<NewsArticle?>?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("totalResults")
        val totalResults: Int?
)

@Parcelize
data class NewsArticle(
        @SerializedName("author")
        val author: String?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("publishedAt")
        val publishedAt: String?,
        @SerializedName("source")
        val source: Source?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?
) : Parcelable

@Parcelize
data class Source(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?
) : Parcelable