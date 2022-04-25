package com.example.newsarticles.Repository

import com.example.newsarticles.APi.RetrofitInstance
import com.example.newsarticles.Models.Article
import com.example.newsarticles.db.ArticleDataBse

class NewsRepository (
    val db: ArticleDataBse
){
    //this function return response
    suspend fun getBreakingNews(countryCoed:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCoed,pageNumber)

    //search function and return response
    suspend fun searchInAPi(searchQuery:String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery=searchQuery,pageNumber)
    //database
    suspend fun upsert(article: Article)=db.getArticleDao().upsert(article)
    fun getAll()=db.getArticleDao().getAllArticles()
    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)
}