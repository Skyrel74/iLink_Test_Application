package com.example.ilink_test_application.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * Entity to store animal pictures
 *
 * @param[url] using to load images from remote data source
 * @param[fileUri] using to load images from local data source
 *
 * @author ilya.rakipov@gmail.com
 */
@Entity(tableName = "animal")
class Animal(
    @PrimaryKey
    @Expose
    val url: String = "",
    var fileUri: String = "",
)
