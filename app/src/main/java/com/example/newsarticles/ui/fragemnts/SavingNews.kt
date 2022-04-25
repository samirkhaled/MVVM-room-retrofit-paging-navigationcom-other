package com.example.newsarticles.ui.fragemnts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticles.R
import com.example.newsarticles.adapters.NewsAdapter
import com.example.newsarticles.databinding.FragmentSavedNewsBinding
import com.example.newsarticles.ui.NewsActivity
import com.example.newsarticles.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavingNews: Fragment(R.layout.fragment_saved_news)
{
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding:FragmentSavedNewsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentSavedNewsBinding.bind(view)
        //inti
        newsViewModel=(activity as NewsActivity).newsViewModel
        setupRecyclerView()
        //handle click
        //handle on clicks
        newsAdapter.setOnItemClickListener {
            val action=SavingNewsDirections.actionSavingNewsToArticleFragment(it)
            findNavController().navigate(action)
        }

       //liveData
       newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer { it->
           newsAdapter.diff.submitList(it)
           newsAdapter.notifyDataSetChanged()
       })

        //rv
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.diff.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        newsViewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.diff.submitList(articles)
        })

    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}