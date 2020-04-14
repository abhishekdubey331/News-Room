package com.doubtnut.assignment.di.viewmodel

import androidx.lifecycle.ViewModel
import com.doubtnut.assignment.di.viewmodel.ViewModelKey
import com.doubtnut.assignment.viewmodel.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    abstract fun bindNewsListViewModel(mainActivityViewModel: NewsListViewModel): ViewModel

}