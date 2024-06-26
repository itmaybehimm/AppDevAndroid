package com.example.appdevsneha.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appdevsneha.data.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note")
    fun readAllNotes():LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :noteId LIMIT 1")
    fun getNoteById(noteId: Int): LiveData<Note>

    @Query("SELECT * FROM note WHERE folder= :folderId")
    fun getNoteByFolderId(folderId:Int):LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE body LIKE :searchQuery")
    fun searchNotes(searchQuery: String): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE body LIKE :searchQuery AND folder = :folderId")
    fun searchNotesInFolder(searchQuery: String, folderId: Int): LiveData<List<Note>>
}