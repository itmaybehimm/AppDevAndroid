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

    @Query("SELECT * FROM note WHERE user = :userId")
    fun readAllNotes(userId:Int):LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :noteId AND user = :userId  LIMIT 1")
    fun getNoteById(noteId: Int,userId:Int): LiveData<Note>

    @Query("SELECT * FROM note WHERE folder= :folderId AND user = :userId")
    fun getNoteByFolderId(folderId:Int,userId:Int):LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE body LIKE :searchQuery AND user = :userId")
    fun searchNotes(searchQuery: String,userId:Int): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE body LIKE :searchQuery AND folder = :folderId AND user = :userId")
    fun searchNotesInFolder(searchQuery: String, folderId: Int,userId:Int): LiveData<List<Note>>
}