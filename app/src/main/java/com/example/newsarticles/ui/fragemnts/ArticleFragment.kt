package com.example.newsarticles.ui.fragemnts

import android.os.Bundle
import android.util.Log

import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.navArgs

import com.example.newsarticles.R
import com.example.newsarticles.adapters.NewsAdapter
import com.example.newsarticles.databinding.ArticleLayoutBinding
import com.example.newsarticles.ui.NewsActivity
import com.example.newsarticles.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers


class ArticleFragment:Fragment(R.layout.article_layout){
    private val args:ArticleFragmentArgs by navArgs()
    private lateinit var newsViewModel:NewsViewModel
     private lateinit var binding:ArticleLayoutBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= ArticleLayoutBinding.bind(view)
        newsViewModel=(activity as NewsActivity).newsViewModel
        val url=args.currentItem.url

            binding.webView.apply {
                settings.javaScriptEnabled=true
                webViewClient=WebViewClient()
                loadUrl(url!!) }

            binding.fab.setOnClickListener {
                try {
                    newsViewModel.saveArticle(args.currentItem)
                    Snackbar.make(view,"Saved",Snackbar.LENGTH_SHORT).show()
                }catch (ex:Exception)
                {
                    Log.d("tag",ex.message.toString())
                }
        }



    }
   inner class MyCallBack:WebViewClient(){
       override fun shouldOverrideUrlLoading(
           view: WebView?,
           request: WebResourceRequest?
       ): Boolean {
           return super.shouldOverrideUrlLoading(view, request)
       }
   }

}