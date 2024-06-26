package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
