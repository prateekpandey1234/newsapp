package models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Entity(//a entity is use dto tell database that we are storing our data as a table in our database
    tableName = "articles"
)

data class Article(//the table's column would be the parameters below
    @PrimaryKey(autoGenerate = true)//this here tells the database to autogenerate the id
    val id :Int?= null,//we kept it as nullable cuz we only want to store id of saved articles as we store them in database
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @IgnoredOnParcel  val source:@RawValue Source?,//it will show error if we add a custom datatype in parcel
    val title: String,
    val url: String,
    val urlToImage: String
)
//Serialization is the process of converting data used by an application
// to a format that can be transferred over a network or stored in a database or a file.