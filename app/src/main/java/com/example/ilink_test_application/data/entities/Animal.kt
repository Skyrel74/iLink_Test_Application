package com.example.ilink_test_application.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "animal")
class Animal(
    @PrimaryKey
    @Expose
    val url: String = "",
    var fileUri: String = "",
)
