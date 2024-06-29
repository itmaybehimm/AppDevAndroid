package com.example.appdevsneha.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appdevsneha.data.model.Folder


@Dao
interface FolderDao {

    @Insert
    suspend fun addFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Query("SELECT * FROM folder WHERE user = :userId")
    fun readAllFolders(userId:Int): LiveData<List<Folder>>

    @Query("SELECT * FROM folder WHERE id= :folderId AND user = :userId LIMIT 1")
    fun getFolderById(folderId:Int,userId:Int):LiveData<Folder>
}