package db

import android.content.Context
import androidx.room.*
import models.Article

@Database(
    entities = [Article::class],//here we pass the entities for our database
    version=1//version is used when we update our database and room migrates the older database to the new database
)
//Room Database is a part of the Android Architecture components
// which provides an abstraction layer over SQLite which allows for
// more robust database access while still providing the full power of SQLite.

@TypeConverters(converters::class)//name of typeconverter
abstract class ArticleDatabase :RoomDatabase(){
    abstract fun getArticleDao() : Articledao

    companion object {
                 //Volatile keyword is used to modify the value of a variable by
                 // different threads. The volatile keyword does not cache the
        @Volatile// value of the variable and always read the variable from the main memory.
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {//hre we check for nullable instance...we use synchronized for
                                                                                //only single time initiation
            instance ?: createDatabase(context).also { instance = it }//here we double check that if our instance is null or not
        }

        private fun createDatabase(context: Context) =//here we create database for our application if instance is null
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}