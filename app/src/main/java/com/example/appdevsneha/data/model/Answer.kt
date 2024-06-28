package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(entity = Quiz::class, parentColumns = ["id"], childColumns = ["quiz"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Question::class, parentColumns = ["id"], childColumns = ["question"], onDelete = ForeignKey.RESTRICT)
    ]
)
data class Answer(
    @ColumnInfo(name = "quiz")
    val quiz:Int,

    @ColumnInfo(name="question")
    val questionId:Int,

    @ColumnInfo(name = "answer")
    val answer:Int?,

    @PrimaryKey(autoGenerate = true)
    val id: Int=0
)
