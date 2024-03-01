package com.mbonetti.tmchallenge.db.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbonetti.tmchallenge.db.models.EmbeddedVenue
import com.mbonetti.tmchallenge.db.models.EventImage

class Converters {

    @TypeConverter
    fun fromJsonToVenue(venueString: String): EmbeddedVenue {
        val type = object : TypeToken<EmbeddedVenue>() {}.type
        return Gson().fromJson(venueString, type)
    }

    @TypeConverter
    fun fromVenueToJson(embeddedVenue: EmbeddedVenue): String {
        return Gson().toJson(embeddedVenue)
    }

    @TypeConverter
    fun fromJsonImageList(imageString: String): List<EventImage> {
        val imageList = object : TypeToken<List<EventImage>>() {}.type
        return Gson().fromJson(imageString, imageList)
    }

    @TypeConverter
    fun fromImageListToJson(imageList: List<EventImage>): String {
        return Gson().toJson(imageList)
    }

}
