package com.example.medicationreminder

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    companion object {
        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun toJson(list: ArrayList<DoseRecord>): String {
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun fromJson(string: String): ArrayList<DoseRecord> {
            val listType: Type = object: TypeToken<ArrayList<DoseRecord>>() {}.type
            return gson.fromJson(string, listType)
        }

        @TypeConverter
        @JvmStatic
        fun timeOfDayListToJson(list: ArrayList<RepeatSchedule>): String {
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun timeOfDayListFromJson(string: String): ArrayList<RepeatSchedule> {
            val listType: Type = object: TypeToken<ArrayList<RepeatSchedule>>() {}.type
            return gson.fromJson(string, listType)
        }
    }
}