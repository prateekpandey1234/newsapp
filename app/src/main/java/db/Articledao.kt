package db

import androidx.lifecycle.LiveData
import androidx.room.*
import models.Article
//When you use the Room persistence library to store your app's data, you interact with the stored data by defining data access
// objects or DAOs. Each DAO includes methods that offer a
// bstract access to your app's database. At compile time, Room automatically generates implementations of the DAOs that you define.

@Dao
interface Articledao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//it refers that if we try to save a article same as another in database
    suspend fun upsert(article: Article):Long//the Long here refers to the id of article

    @Query("SELECT*FROM articles")
    fun getarticle():LiveData<List<Article>>//THE liveData method notifies every observer or the database to subscribe to the changes
                                            //made in the article which is inside a list
    @Delete
    suspend fun deletearticle(article: Article)
}