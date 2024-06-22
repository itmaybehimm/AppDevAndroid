package com.example.appdevsneha

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "body")
    val body:String,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
