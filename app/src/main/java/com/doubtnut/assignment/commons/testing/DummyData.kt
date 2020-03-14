package com.doubtnut.assignment.commons.testing

import androidx.annotation.VisibleForTesting
import com.doubtnut.assignment.commons.data.NewsStory


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    fun getNewsStory() = NewsStory(mutableListOf(), "", 2)
}