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
            setBody(DependencyProvider.getResponseFromJson("repos"))
        }

        newsStoryService
                .getNewsStories()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                   // Assert.assertEquals(values()[0].size, 25)
                }
    }


    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}