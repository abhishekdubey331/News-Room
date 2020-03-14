package com.doubtnut.assignment.viewmodel


import androidx.lifecycle.Observer
import android.os.Build
import com.core.base.networking.Outcome
import com.doubtnut.assignment.commons.data.NewsStory
import com.doubtnut.assignment.commons.testing.DummyData
import com.doubtnut.assignment.model.NewsListDataContract
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * Tests for [NewsListViewModel]
 * */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class NewsListViewModelTest {

    private lateinit var viewModel: NewsListViewModel
    private val repo: NewsListDataContract.Repository = mock()
    private val outcome: Observer<Outcome<NewsStory>> = mock()

    @Before
    fun init() {
        viewModel = NewsListViewModel(repo, CompositeDisposable())
        whenever(repo.postFetchOutcome).doReturn(PublishSubject.create())
        viewModel.postsOutcome.observeForever(outcome)
    }


    @Test
    fun testGetPostsSuccess() {
        viewModel.getNewsStories()
        verify(repo).fetchNewsStories()

        repo.postFetchOutcome.onNext(Outcome.loading(true))
        verify(outcome).onChanged(Outcome.loading(true))

        repo.postFetchOutcome.onNext(Outcome.loading(false))
        verify(outcome).onChanged(Outcome.loading(false))

        val data = DummyData.getNewsStory()
        repo.postFetchOutcome.onNext(Outcome.success(data))
        verify(outcome).onChanged(Outcome.success(data))
    }


    @Test
    fun testGetPostsError() {
        val exception = IOException()
        repo.postFetchOutcome.onNext(Outcome.failure(exception))
        verify(outcome).onChanged(Outcome.failure(exception))
    }


    @Test
    fun testRefreshPosts() {
        viewModel.refreshNewsStories()
        verify(repo).fetchNewsStories()
    }
}