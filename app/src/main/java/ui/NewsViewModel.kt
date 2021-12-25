package ui
//a work of ViewModel is to act as bridge between View and Model ...it mostly stores the behind code stuff
//we use to manipulate our view/UI
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.NewsResponse
import repository.newsrepo
import retrofit2.Response
import util.Resource

//we can not give constructive parameters in the view model so we need to create a ViewModelProvider file
//which gives returns a factory method
//the reason is that when we implement viewmodel in activity the ViewModelProvider(Interface) method internally calls the primary constructor
//of our ViewModel and returns the instance of ViewModel
//and when we give a argument inside ViewModel it will show error hence we need to create our own
//ViewModelProviderFactory method
class NewsViewModel(
    val repository: newsrepo
):ViewModel() {

    //it used to store whether our response is success or error or Loading
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
//    MutableLiveData is a subclass of LiveData which is used for some of it's
//    properties (setValue/postValue) and using these properties we can easily notify
//    the ui when onChange() is called. Only using LiveData object we can't do this
    val breakingNewsPage:Int = 1
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    init {
        getBreakingNews("in","en")
    }
    //the getBreakingNews function is a suspend function in Repository hence we use it in a
    //coroutine

    fun getBreakingNews(country: String,language:String) = viewModelScope.launch {
        //the launchscope here means that the coroutine will only exist until ViewModel exists
        //this here allows fragments to observe the changes made in the LiveData
        breakingNews.postValue(Resource.Loading())
        val response = repository.getbreakingNews(country , breakingNewsPage,language)
        //here we get the response from api

        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.getsearchNews(searchQuery, breakingNewsPage,"en")
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    // in this function we handle the state of our response whether it is loading or success or error
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let {

                return Resource.Success(it)//it here means the NewsResponse
            }
        }
        Log.d("api-lvl","${response.toString()}")
        return Resource.Error(response.message())
    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}