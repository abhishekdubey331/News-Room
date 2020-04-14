package com.doubtnut.assignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.base.networking.Scheduler
import com.core.base.utils.ImageHelper
import com.core.base.utils.ToolbarHelper
import com.doubtnut.assignment.commons.data.NewsStoryService
import com.doubtnut.assignment.model.NewsListDataContract
import com.doubtnut.assignment.model.NewsListRemoteData
import com.doubtnut.assignment.model.NewsRepository
import com.doubtnut.assignment.di.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@RepositoryScope
class TrendingRepositoryModule {

    @Provides
    fun repositoryService(retrofit: Retrofit): NewsStoryService = retrofit.create(NewsStoryService::class.java)

    @Provides
    fun trendingRepoList(remote: NewsListDataContract.Remote, scheduler: Scheduler, compositeDisposable: CompositeDisposable): NewsListDataContract.Repository = NewsRepository(remote, scheduler, compositeDisposable)

    @Provides
    fun remoteData(postService: NewsStoryService): NewsListDataContract.Remote = NewsListRemoteData(postService)

    @Provides
    @Singleton
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(providerMap)
    }

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun toolbarHelper(): ToolbarHelper = ToolbarHelper()

    @Provides
    fun imageHelper(): ImageHelper = ImageHelper()
}