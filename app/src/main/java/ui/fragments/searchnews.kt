package ui.fragments

import adapters.NewsAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp3.R
import db.ArticleDatabase
import kotlinx.android.synthetic.main.fragment_searchnews.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.newsrepo
import ui.MainActivity
import ui.NewsViewModel
import ui.NewsViewModelProviderFactory
import util.Resource
import util.constants.Companion.SEARCH_NEWS_TIME_DELAY

class searchnews : Fragment(R.layout.fragment_searchnews) {
    lateinit var viewmodel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository =
            newsrepo(ArticleDatabase(context = requireContext()))//go check invoke method in database
        val viewmodelprovider = NewsViewModelProviderFactory(repository)
        viewmodel = ViewModelProvider(this, viewmodelprovider).get(NewsViewModel::class.java)
        setupRecyclerView()

        // here we make our article as an bundle and pass it to nav_graph so they can navigate as in xml file
//        newsAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("article",it)
//            }
//            findNavController().navigate(
//                R.id.action_searchnews_to_articlenews,
//                bundle
//            )
//        }
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->//whenever text changes in search box
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)//we added delay so not many requests are given
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewmodel.searchNews(editable.toString())
                    }
                }
            }

            viewmodel.searchNews.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        }
    }

     private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
