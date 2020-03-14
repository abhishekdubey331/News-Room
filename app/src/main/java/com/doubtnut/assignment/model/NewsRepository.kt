package com.doubtnut.assignment.model

import com.core.base.extensions.addTo
import com.core.base.extensions.failed
import com.core.base.extensions.loading
import com.core.base.extensions.performOnBackOutOnMain
import com.core.base.extensions.success
import com.core.base.networking.Outcome
import com.core.base.networking.Scheduler
import com.doubtnut.assignment.commons.data.NewsStory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


class NewsRepository(
        private val remote: NewsListDataContract.Remote,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable
) : NewsListDataContract.Repository {

    override val postFetchOutcome: PublishSubject<Outcome<NewsStory>> = PublishSubject.create()


    override fun fetchNewsStories() {
        postFetchOutcome.loading(true)
        remote.getNewsStories()
                .performOnBackOutOnMain(scheduler)
                .subscribe({ postsWithUsers ->
                    postFetchOutcome.success(postsWithUsers)
                }, { error -> handleError(error) })
                .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        postFetchOutcome.failed(error)
    }
}