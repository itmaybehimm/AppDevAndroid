package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "folder")
data class Folder (
    @ColumnInfo(name="name")
    val name:String,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)