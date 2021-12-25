package API

import API.NEWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.constants.Companion.BASE_URL

//we create singleton as we only want to intiate only one time
class Retrofitsingleton {
    companion object{
        private val retrofit by lazy { //lazy allows us to create variable only when object is created
            val logging = HttpLoggingInterceptor()//ALLOWS TO SEE THE REQUESTS AND RESPONSES
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//converts gson data
                .client(client)
                .build()
        }
        val api by lazy{
            retrofit.create(NEWS::class.java)//here we create the requests from server
        }
    }
}