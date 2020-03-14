package com.doubtnut.assignment.model

import com.doubtnut.assignment.commons.data.NewsStoryService
import com.doubtnut.assignment.commons.data.NewsStory
import io.reactivex.Single


class NewsListRemoteData(private val newsStoryService: NewsStoryService) : NewsListDataContract.Remote {

    override fun getNewsStories(): Single<NewsStory> {
        return newsStoryService.getNewsStories()
    }
}