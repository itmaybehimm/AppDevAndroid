package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "note",
    foreignKeys = [
        ForeignKey(entity = Folder::class, parentColumns = ["id"], childColumns = ["folder"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Note(
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "body")
    val body:String,


    @ColumnInfo(name="user")
    val userId:Int,

    @ColumnInfo(name="folder")
    val folderId:Int?=null,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
