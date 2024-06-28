package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz(
    @ColumnInfo("score")
    val score:Int=0,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
