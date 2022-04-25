package com.example.newsarticles.APi

import com.example.newsarticles.Models.NewsResponse
import com.example.newsarticles.Util.Constant.Companion.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewApi {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = ApiKey
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("pageNumber")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = ApiKey
    ): Response<NewsResponse>

}