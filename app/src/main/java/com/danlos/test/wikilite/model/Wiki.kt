package com.danlos.test.wikilite.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "wikis")
data class Wiki(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @SerializedName("pageid") val pageId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("snippet") val snippet: String
    )