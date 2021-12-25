package ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp3.R
import db.ArticleDatabase
import repository.newsrepo
import ui.MainActivity
import ui.NewsViewModel
import ui.NewsViewModelProviderFactory

class savednews : Fragment(R.layout.fragment_savednews) {
    lateinit var viewmodel: NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = newsrepo(ArticleDatabase(context = requireContext()))//go check invoke method in database
        val viewmodelprovider= NewsViewModelProviderFactory(repository)
        viewmodel = ViewModelProvider(this,viewmodelprovider).get(NewsViewModel::class.java)
    }
}