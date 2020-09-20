package com.api.articles.apiInterface

import com.api.articles.model.Article
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    @GET("v1/blogs")
    fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    )
            : Observable<List<Article>>

}