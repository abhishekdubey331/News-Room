package  com.doubtnut.assignment.remote

import android.os.Build
import com.doubtnut.assignment.commons.data.NewsStoryService
import com.doubtnut.assignment.commons.testing.DummyData
import com.doubtnut.assignment.model.NewsListRemoteData
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for [NewsListRemoteData]
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class NewsListRemoteDataTest {

    private val newsStoryService = mock<NewsStoryService>()

    @Test
    fun getPosts() {
        whenever(newsStoryService.getNewsStories()).thenReturn(
                Single.just(DummyData.getNewsStory())
        )

        NewsListRemoteData(newsStoryService).getNewsStories().test().run {
            assertNoErrors()
            assertValueCount(1)
        }
    }

}