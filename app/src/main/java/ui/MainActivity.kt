package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import db.ArticleDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import repository.newsrepo
import ui.fragments.fragment_breaking
import android.R

import androidx.annotation.NonNull

import com.google.android.material.bottomnavigation.BottomNavigationView
import ui.fragments.savednews
import ui.fragments.searchnews
//add below code in androidmanifest it will not disappear you search view
//<activity android:windowSoftInputMode="adjustPan"> </activity>
class MainActivity : AppCompatActivity() {
    lateinit var viewmodel:NewsViewModel
    var breaking = fragment_breaking()
    var search = searchnews()
    var savedn = savednews()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.newsapp3.R.layout.activity_main)

        val repo = newsrepo(ArticleDatabase.invoke(this))
        val repository = newsrepo(ArticleDatabase(this))//go check invoke method in database
        val viewmodelprovider=NewsViewModelProviderFactory(repository)
        viewmodel = ViewModelProvider(this,viewmodelprovider).get(NewsViewModel::class.java)
        //Creates ViewModelProvider which will create  ViewModels via the given
        //Factory
        runBlocking { Log.d("api-unit","${repo.toString()}") }
//        val breaking = fragment_breaking()
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.bottomNavigationView2,breaking)
//            commit()
//        }
        bottomNavigationView2.setupWithNavController(news_frag.findNavController())
        supportFragmentManager.beginTransaction().apply {
            replace(com.example.newsapp3.R.id.news_frame,breaking)
            commit()
        }

        bottomNavigationView2.setOnNavigationItemSelectedListener{ item ->
                when (item.itemId) {
                    com.example.newsapp3.R.id.fragment_breaking-> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(com.example.newsapp3.R.id.news_frame,breaking)
                            commit()
                        }
                    }
                    com.example.newsapp3.R.id.searchnews -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(com.example.newsapp3.R.id.news_frame,search)
                            commit()
                        }
                    }
                    com.example.newsapp3.R.id.savednews -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(com.example.newsapp3.R.id.news_frame,savedn)
                            commit()
                        }
                    }

                }
                true
            }
    }
}