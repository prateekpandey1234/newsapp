package API

import models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ui.fragments.news_article
import util.constants.Companion.API_KEY

interface NEWS {
    @GET("v2/top-headlines")//it requests from website for top-headlines
    suspend fun getnews(
        @Query("country")
        country:String = "us",//the query here refers to the query from the documentation of website
        @Query("from")
        date:String="2021-12-21",
        @Query("language")
        language:String="en",
        @Query("page")
        pagenum:Int = 1,
        @Query("apikey")
        apikey :String=API_KEY
    ):Response<NewsResponse>//here it will return a response when info received from site

    @GET("v2/everything")
    suspend fun search(
        @Query("q")
        querysearch:String,
        @Query("page")
        pagenum:Int = 1,
        @Query("apikey")
        apikey :String=API_KEY
    ):Response<NewsResponse>
}