package com.example.appdevsneha.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "question")
data class Question(
    @ColumnInfo(name = "question")
    val question:String,

    @ColumnInfo(name="optionA")
    val optionA:String,

    @ColumnInfo(name="optionB")
    val optionB:String,

    @ColumnInfo(name="optionC")
    val optionC:String,

    @ColumnInfo(name="optionD")
    val optionD:String,

    @ColumnInfo(name="answer")
    val answer:Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int=0
)
