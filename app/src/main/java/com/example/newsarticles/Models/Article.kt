package com.example.newsarticles.Models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "Articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val ID:Int?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Parcelable