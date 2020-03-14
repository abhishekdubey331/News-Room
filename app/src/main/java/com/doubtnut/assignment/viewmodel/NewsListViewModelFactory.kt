package com.doubtnut.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubtnut.assignment.model.NewsListDataContract
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class NewsListViewModelFactory(private val repository: NewsListDataContract.Repository, private val compositeDisposable: CompositeDisposable) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(repository, compositeDisposable) as T
    }
}