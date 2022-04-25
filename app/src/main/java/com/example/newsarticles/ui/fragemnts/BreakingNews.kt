package com.example.newsarticles.ui.fragemnts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticles.R
import com.example.newsarticles.Util.Constant.Companion.QUERY_PAGE_SIZE
import com.example.newsarticles.Util.Resource
import com.example.newsarticles.adapters.NewsAdapter
import com.example.newsarticles.databinding.FragmentBreakingNewsBinding
import com.example.newsarticles.ui.NewsActivity
import com.example.newsarticles.ui.NewsViewModel
class BreakingNews: Fragment(R.layout.fragment_breaking_news) {
     lateinit var breakingNewsViewModel:NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding:FragmentBreakingNewsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding = FragmentBreakingNewsBinding.bind(view)
        breakingNewsViewModel = (activity as NewsActivity).newsViewModel
        setupRecyclerView()
            breakingNewsViewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.diff.submitList(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = breakingNewsViewModel.breakingNewsPage == totalPages
                            if (isLastPage)
                            {
                                binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                            }

                        }

                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Log.d("tag", "Error :$it")
                            Toast.makeText(activity,it ,Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        //handle on clicks
        newsAdapter.setOnItemClickListener {
            val action=BreakingNewsDirections.actionBreakingNewsToArticleFragment(it)
            findNavController().navigate(action)

        }

        }

        private fun setupRecyclerView() {
            newsAdapter = NewsAdapter()
            binding.rvBreakingNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(activity)
                addOnScrollListener(this@BreakingNews.scrollListener)
            }

        }

        private fun hideProgressBar() {
            binding.paginationProgressBar.visibility = View.INVISIBLE
            isLoading = false
        }

        private fun showProgressBar() {
            binding.paginationProgressBar.visibility = View.VISIBLE
            isLoading = true
        }
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                breakingNewsViewModel.getBreakingNews("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }
}
