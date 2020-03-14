package com.doubtnut.assignment.di

import com.core.base.di.CoreComponent
import com.core.base.networking.Scheduler
import com.core.base.utils.ToolbarHelper
import com.doubtnut.assignment.ui.activity.ParentActivity
import com.doubtnut.assignment.commons.data.NewsStoryService
import com.doubtnut.assignment.model.NewsListDataContract
import com.doubtnut.assignment.model.NewsRepository
import com.doubtnut.assignment.model.NewsListRemoteData
import com.doubtnut.assignment.ui.fragments.NewsListFragment
import com.doubtnut.assignment.viewmodel.NewsListViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@RepositoryScope
@Component(dependencies = [CoreComponent::class], modules = [TrendingRepositoryModule::class])
interface NewsStoryComponent {

    fun repositoryService(): NewsStoryService
    fun scheduler(): Scheduler
    fun inject(listActivity: NewsListFragment)
}

@Module
@RepositoryScope
class TrendingRepositoryModule {
    @Provides
    @RepositoryScope
    fun repositoryService(retrofit: Retrofit): NewsStoryService = retrofit.create(NewsStoryService::class.java)


    @Provides
    @RepositoryScope
    fun trendingRepoList(remote: NewsListDataContract.Remote, scheduler: Scheduler, compositeDisposable: CompositeDisposable): NewsListDataContract.Repository = NewsRepository(remote, scheduler, compositeDisposable)

    @Provides
    @RepositoryScope
    fun remoteData(postService: NewsStoryService): NewsListDataContract.Remote = NewsListRemoteData(postService)


    /*ViewModel*/
    @Provides
    @RepositoryScope
    fun trendingRepoListViewModelFactory(repository: NewsListDataContract.Repository, compositeDisposable: CompositeDisposable): NewsListViewModelFactory = NewsListViewModelFactory(repository, compositeDisposable)


    @Provides
    @RepositoryScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @RepositoryScope
    fun toolbarHelper(): ToolbarHelper = ToolbarHelper()
}