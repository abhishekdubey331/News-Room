package com.doubtnut.assignment.commons

import com.core.base.application.CoreApp
import com.doubtnut.assignment.di.DaggerNewsStoryComponent
import com.doubtnut.assignment.di.NewsStoryComponent
import javax.inject.Singleton

@Singleton
object NewsStoriesDH {
    private var newsStoryComponent: NewsStoryComponent? = null

    fun newsStoryComponent(): NewsStoryComponent {
        if (newsStoryComponent == null)
            newsStoryComponent = DaggerNewsStoryComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return newsStoryComponent as NewsStoryComponent
    }

    fun destroyNewsStoryComponent() {
        newsStoryComponent = null
    }
}