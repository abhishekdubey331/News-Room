package com.doubtnut.assignment.commons.data

import io.reactivex.Single
import retrofit2.http.GET


interface NewsStoryService {

    @GET("top-headlines?country=us&apiKey=1de92a1a54614fac89c90135633c45c6")
    fun getNewsStories(): Single<NewsStory>
}