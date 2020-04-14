package com.doubtnut.assignment.di

import com.core.base.di.CoreComponent
import com.core.base.networking.Scheduler
import com.doubtnut.assignment.commons.data.NewsStoryService
import com.doubtnut.assignment.ui.fragments.NewsDetailFragment
import com.doubtnut.assignment.ui.fragments.NewsListFragment
import dagger.Component

@RepositoryScope
@Component(dependencies = [CoreComponent::class], modules = [TrendingRepositoryModule::class,ViewModelModule::class])
interface NewsStoryComponent {

    fun repositoryService(): NewsStoryService
    fun scheduler(): Scheduler
    fun inject(listActivity: NewsListFragment)
    fun inject(listActivity: NewsDetailFragment)
}
