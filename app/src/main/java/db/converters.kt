package db

import androidx.room.TypeConverter
import models.Source
//we use typeconverters because our database only works with primary datatypes not user-defined classes
//we should create 2 typeconverters from one type to other and vice versa
class converters {
    @TypeConverter
    fun fromsource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun tosource(name1:String): Source {
        return Source(name1,name1)
    }

}