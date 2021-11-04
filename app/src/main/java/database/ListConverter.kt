package database

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromListToOne(value :List<String>): String {
        var string = ""
        for(i in value.indices){
            string+=value[i]
            if  (i != value.size-1)
            string+="|"
        }
        return string
    }
    @TypeConverter
    fun fromOneToList(value :String): List<String> {
        return value.split("|")
    }
}