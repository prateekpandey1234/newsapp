package repository
//repository is also referred as Model in MVVM architectural design/skeleton of our app
//it mostly consists of the data we gather from either a database or API or both of them
import API.Retrofitsingleton
import android.util.Log
import db.ArticleDatabase
import models.NewsResponse
import retrofit2.Response
import ui.fragments.news_article

class newsrepo(
    val db:ArticleDatabase
) {
    //it is networking function hence we use suspend method
    suspend fun getbreakingNews(Country:String,Page:Int,lang:String): Response<NewsResponse> {
        Log.d("retro-api","${Retrofitsingleton.api.getnews(country = Country,pagenum=Page, language = lang)}")
        return Retrofitsingleton.api.getnews(country = Country,pagenum=Page, language = lang)

    }
    suspend fun getsearchNews(value:String,Page:Int,lang:String): Response<NewsResponse> {
        return Retrofitsingleton.api.search(querysearch = value, pagenum = Page)

    }
}

