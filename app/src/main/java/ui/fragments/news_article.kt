package ui.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp3.R
import db.ArticleDatabase
import kotlinx.android.synthetic.main.articlenews.*
import models.Article
import repository.newsrepo
import ui.MainActivity
import ui.NewsViewModel
import ui.NewsViewModelProviderFactory

public class news_article : Fragment(R.layout.article_preview) {

    lateinit var viewmodel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = newsrepo(ArticleDatabase(context = requireContext()))//go check invoke method in database
        val viewmodelprovider= NewsViewModelProviderFactory(repository)
        viewmodel = ViewModelProvider(this,viewmodelprovider).get(NewsViewModel::class.java)
//        val article = args.article
//        webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(article.toString())
//        }


    }



}