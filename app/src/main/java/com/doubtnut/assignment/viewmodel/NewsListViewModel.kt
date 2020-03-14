package com.doubtnut.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.core.base.extensions.toLiveData
import com.core.base.networking.Outcome
import com.doubtnut.assignment.commons.NewsStoriesDH
import com.doubtnut.assignment.commons.data.NewsStory
import com.doubtnut.assignment.model.NewsListDataContract
import io.reactivex.disposables.CompositeDisposable

class NewsListViewModel(private val repo: NewsListDataContract.Repository,
                        private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val postsOutcome: LiveData<Outcome<NewsStory>> by lazy {
        repo.postFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getNewsStories() {
        if (postsOutcome.value == null)
            repo.fetchNewsStories()
    }

    fun refreshNewsStories() {
        repo.fetchNewsStories()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        NewsStoriesDH.destroyNewsStoryComponentt()
    }
}