package com.example.appdevsneha

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)
}