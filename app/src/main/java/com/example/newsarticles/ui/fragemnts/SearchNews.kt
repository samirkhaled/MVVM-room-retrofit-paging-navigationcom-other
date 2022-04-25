package com.example.newsarticles.ui.fragemnts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsarticles.R
import com.example.newsarticles.Util.Constant.Companion.SEARCH_VIEW_TIME_DELAY
import com.example.newsarticles.Util.Resource
import com.example.newsarticles.adapters.NewsAdapter
import com.example.newsarticles.databinding.FragmentSearchNewsBinding
import com.example.newsarticles.ui.NewsActivity
import com.example.newsarticles.ui.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNews: Fragment(R.layout.fragment_search_news)
{
    lateinit var searchNewsViewModel: NewsViewModel
   private lateinit var binding:FragmentSearchNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    override   fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    binding= FragmentSearchNewsBinding.inflate(inflater, container, false)
       //view model
        searchNewsViewModel= (activity as NewsActivity).newsViewModel
        newsAdapter= NewsAdapter()
        //setup rv
        setupRecyclerView()

        //
        //handle on clicks
        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                val action=SearchNewsDirections.actionSearchNewsToArticleFragment(it)
                findNavController().navigate(action)
            }
        }
        //search
        var job:Job?=null
        binding.etSearch.addTextChangedListener {it->
            job?.cancel()
            job=lifecycleScope.launchWhenCreated {
                delay(SEARCH_VIEW_TIME_DELAY)
                it?.let {
                    if(it.toString().isNotEmpty())
                    {
                        searchNewsViewModel.searchNews(it.toString())
                    }
                }
                job?.join()
            }

        }

        //response
        searchNewsViewModel.searchNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.diff.submitList(newsResponse.articles)
                        newsAdapter.notifyDataSetChanged()
                        Log.d("tag ",newsResponse.toString())
                    }

                }
                is Resource.Error->
                {
                    hideProgressBar()
                    response.message?.let {
                        Log.d("tag", "Error :$it")
                        Toast.makeText(activity,it , Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading->
                {
                    showProgressBar()
                }
            }
        })
    return binding.root
    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility= View.INVISIBLE

    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility= View.VISIBLE

    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvSearchNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }

    }
}