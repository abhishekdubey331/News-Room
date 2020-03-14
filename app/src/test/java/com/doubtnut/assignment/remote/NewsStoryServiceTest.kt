package com.doubtnut.assignment.remote

import android.os.Build
import com.core.base.testing.DependencyProvider
import com.doubtnut.assignment.commons.data.NewsStoryService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * Tests for [NewsStoryService]
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class NewsStoryServiceTest {

    private lateinit var newsStoryService: NewsStoryService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        newsStoryService = DependencyProvider
                .getRetrofit(mockWebServer.url("/"))
                .create(NewsStoryService::class.java)

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun getTrendingRepos() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("sample_news"))
        }

        newsStoryService
                .getNewsStories()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    Assert.assertEquals(values()[0].totalResults, 38)
                    Assert.assertEquals(values()[0].newsArticles?.get(0)?.author, "Igor Bonifacic")
                    Assert.assertEquals(values()[0].newsArticles?.get(1)?.title, "Call of Duty: Warzone world records for most kills - Dexerto")
                    Assert.assertEquals(values()[0].newsArticles?.get(2)?.publishedAt, "2020-03-14T10:43:33Z")
                }
    }


    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}