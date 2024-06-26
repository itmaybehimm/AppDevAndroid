package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(entity = Quiz::class, parentColumns = ["id"], childColumns = ["quiz"], onDelete = ForeignKey.CASCADE),
    ]
)
data class Answer(
    @ColumnInfo(name = "quiz")
    val quiz:Int,

    @ColumnInfo(name = "answer")
    val answer:Int,

    @PrimaryKey(autoGenerate = true)
    val int: Int=0
)
