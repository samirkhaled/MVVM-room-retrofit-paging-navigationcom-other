package com.example.newsarticles.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.NewsApplication
import com.example.newsarticles.Repository.NewsRepository



class NewsViewModelProvidersFactory(
    val newsRepository: NewsRepository,
    val app:Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository,app) as T
    }
}