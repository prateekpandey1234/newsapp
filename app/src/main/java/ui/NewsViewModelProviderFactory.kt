package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import repository.newsrepo

class NewsViewModelProviderFactory(
    val repository : newsrepo
):ViewModelProvider.Factory {
    //the method down here is responsible of creating instance of our ViewModel

    //here we return a new instance with repository as an argument for our ViewModel

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }


}