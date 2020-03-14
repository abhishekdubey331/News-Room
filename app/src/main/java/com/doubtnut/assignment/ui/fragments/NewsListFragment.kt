package com.doubtnut.assignment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.core.base.application.BaseFragment
import com.core.base.extensions.gone
import com.core.base.extensions.makeVisible
import com.core.base.extensions.provideCache
import com.core.base.extensions.tryNavigate
import com.core.base.networking.Outcome
import com.core.base.utils.ImageHelper
import com.core.base.utils.ToolbarHelper
import com.doubtnut.assignment.R
import com.doubtnut.assignment.adapter.NewsListAdapter
import com.doubtnut.assignment.commons.NewsStoriesDH
import com.doubtnut.assignment.commons.data.NewsStory
import com.doubtnut.assignment.ui.activity.ParentActivity
import com.doubtnut.assignment.viewmodel.NewsListViewModel
import com.doubtnut.assignment.viewmodel.NewsListViewModelFactory
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.retry_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class NewsListFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val component by lazy { NewsStoriesDH.newsStoryComponent() }

    @Inject
    lateinit var viewModelFactoryNews: NewsListViewModelFactory

    @Inject
    lateinit var toolbarHelper: ToolbarHelper

    @Inject
    lateinit var imageHelper: ImageHelper

    private lateinit var adapter: NewsListAdapter
    private val viewModel: NewsListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactoryNews).get(NewsListViewModel::class.java)
    }

    override val layout: Int = R.layout.fragment_news_list

    override fun setToolbar(view: View) {
        toolbarHelper.setToolbarInFragment(base_toolbar, activity as ParentActivity, getString(R.string.app_name), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        setToolbar(view)
        viewModel.getNewsStories()
        initiateDataListener()
        srlTrendingRepos.setOnRefreshListener(this)
        retry_button.setOnClickListener {
            viewModel.refreshNewsStories()
        }
    }

    private fun initiateDataListener() {
        viewModel.postsOutcome.observe(this, Observer { outcome ->
            when (outcome) {
                is Outcome.Progress -> srlTrendingRepos.isRefreshing = outcome.loading

                is Outcome.Success -> {
                    context?.let {
                        setAdapterToRecyclerView(outcome.data, it)
                    }
                }

                is Outcome.Failure -> {
                    retry_layout.makeVisible()
                }
            }
        })
    }

    private fun setAdapterToRecyclerView(data: NewsStory, context: Context) {
        adapter = NewsListAdapter(data.newsArticles, context, imageHelper) { newsArticle ->
            tryNavigate(NewsListFragmentDirections.newsListToNewsDetail(newsArticle))
        }
        rvTrendingRepos.adapter = adapter
        (rvTrendingRepos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        srlTrendingRepos.isRefreshing = false
        retry_layout.gone()
    }

    override fun onRefresh() {
        viewModel.refreshNewsStories()
    }

}
