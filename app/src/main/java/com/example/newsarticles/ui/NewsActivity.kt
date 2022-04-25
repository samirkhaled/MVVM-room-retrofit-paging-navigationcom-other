package com.example.newsarticles.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsarticles.R
import com.example.newsarticles.Repository.NewsRepository
import com.example.newsarticles.databinding.ActivityNewsBinding
import com.example.newsarticles.db.ArticleDataBse
import com.example.newsarticles.ui.fragemnts.BreakingNews
import com.google.android.material.bottomnavigation.BottomNavigationView


class NewsActivity : AppCompatActivity() {
  lateinit var newsViewModel:NewsViewModel
   private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDataBse(this))
        val viewModelProviderFactory = NewsViewModelProvidersFactory(newsRepository,application)
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment =supportFragmentManager.findFragmentById(R.id.fragment)  as NavHostFragment
        val navController = navHostFragment.navController
         binding.bottomNavigationView.setupWithNavController(navController)
       // NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)
        val appbar= AppBarConfiguration.Builder(
            R.id.breakingNews,R.id.savingNews,R.id.searchNews
        ).build()
        NavigationUI.setupActionBarWithNavController(this,navController,appbar)

     }

}