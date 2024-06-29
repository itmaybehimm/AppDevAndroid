package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "folder",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user"], onDelete = ForeignKey.CASCADE)
    ]
    )
data class Folder (
    @ColumnInfo(name="name")
    val name:String,

    @ColumnInfo(name="user")
    val userId:Int,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)