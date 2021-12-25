package ui.fragments

import adapters.NewsAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newsapp3.R
import db.ArticleDatabase
import kotlinx.android.synthetic.main.fragment_breaking.*
import models.NewsResponse
import repository.newsrepo
import ui.MainActivity
import ui.NewsViewModel
import ui.NewsViewModelProviderFactory
import util.Resource

class fragment_breaking : Fragment(R.layout.fragment_breaking) {

    lateinit var viewmodel: NewsViewModel
    lateinit var newsadapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = newsrepo(ArticleDatabase(context = requireContext()))//go check invoke method in database
        val viewmodelprovider= NewsViewModelProviderFactory(repository)
        viewmodel = ViewModelProvider(this,viewmodelprovider).get(NewsViewModel::class.java)

        setupRecyclerView()
        // here we make our article as an bundle and pass it to nav_graph so they can navigate as in xml file
        newsadapter.setOnItemClickListener {
            Log.d("gay","$it")

        }
        //here the observer method is called when there is some change and updates the recycleview
        viewmodel.breakingNews.observe(viewLifecycleOwner, Observer { response:Resource<NewsResponse> ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse:NewsResponse ->//can also write newsResponse->...no need
                                                                     //to mention datatype
                        newsadapter.differ.submitList(newsResponse.articles)
                        //from adapter method
                        Log.d("nigga","${newsResponse.articles}")
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message:String->
                        Log.e("dick", "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    Log.d("wtf","$response")
                    showProgressBar()
                }
            }
        })
    }
    private fun setupRecyclerView() {
        newsadapter = NewsAdapter()
        rvBreakingNews.apply {//here we apply recycleview in the fragment from xml file
            adapter = newsadapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        //the id of progress bar in xml layout file
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

}