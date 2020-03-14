package com.doubtnut.assignment.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.core.base.application.BaseFragment
import com.core.base.extensions.thisOrEmpty
import com.core.base.utils.ImageHelper
import com.core.base.utils.ToolbarHelper

import com.doubtnut.assignment.R
import com.doubtnut.assignment.commons.NewsStoriesDH
import com.doubtnut.assignment.ui.activity.ParentActivity
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class NewsDetailFragment : BaseFragment() {

    private val component by lazy { NewsStoriesDH.newsStoryComponent() }

    override val layout: Int = R.layout.fragment_news_detail

    private val args: NewsDetailFragmentArgs by navArgs()

    @Inject
    lateinit var toolbarHelper: ToolbarHelper

    @Inject
    lateinit var imageHelper: ImageHelper


    override fun setToolbar(view: View) {
        toolbarHelper.setToolbarInFragment(base_toolbar, activity as ParentActivity, getString(R.string.full_story), true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        setToolbar(view)
        newsStory.text = args.newsArticle.description.thisOrEmpty()
        args.newsArticle.urlToImage?.let {
            imageHelper.loadImage(requireActivity().applicationContext, it, newsImageView)
        }
        more_info.setOnClickListener {
            args.newsArticle.url?.let {
                launchUrl(it)
            }
        }
        base_toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }
}
