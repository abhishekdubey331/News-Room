package com.doubtnut.assignment.model

import com.core.base.networking.Outcome
import com.doubtnut.assignment.commons.data.NewsStory
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface NewsListDataContract {
    interface Repository {
        val postFetchOutcome: PublishSubject<Outcome<NewsStory>>
        fun fetchNewsStories()
        fun handleError(error: Throwable)
    }

    interface Remote {
        fun getNewsStories() :  Single<NewsStory>
    }
}