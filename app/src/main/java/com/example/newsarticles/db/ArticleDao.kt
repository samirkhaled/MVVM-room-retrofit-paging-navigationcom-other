package com.example.newsarticles.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsarticles.Models.Article


@Dao
interface ArticleDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsert(article: Article):Long
    //get all
    @Query("select * from articles")
      fun getAllArticles(): LiveData<List<Article>>
    //delete
    @Delete
    suspend fun deleteArticle(article: Article)

}