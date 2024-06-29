package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "quiz",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user"], onDelete = ForeignKey.CASCADE)
    ])
data class Quiz(
    @ColumnInfo("score")
    val score:Int=0,

    @ColumnInfo(name="user")
    val userId:Int,

    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
