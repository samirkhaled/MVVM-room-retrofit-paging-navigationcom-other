package com.example.newsarticles.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsarticles.Models.Article

@Database(
    entities = [Article::class],
    version = 3


)
@TypeConverters(Convertes::class)
abstract class ArticleDataBse: RoomDatabase() {

    abstract fun getArticleDao():ArticleDao
    companion object
    {
        @Volatile
        private var instance:ArticleDataBse? =null
        private val LOCK=Any()
        //when object or anything will created
        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?:createDB(context).also{ instance=it}
        }
        private fun createDB(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDataBse::class.java,
                "articleDB"
            ).fallbackToDestructiveMigration()
                .build()
    }

}