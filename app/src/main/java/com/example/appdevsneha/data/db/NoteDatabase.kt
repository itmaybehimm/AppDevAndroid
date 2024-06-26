package com.example.appdevsneha.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appdevsneha.data.model.Folder
import com.example.appdevsneha.data.model.Note
import kotlin.concurrent.Volatile


@Database(
    entities = [Note::class,Folder::class],
    version = 2
)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao



    companion object{
        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(context, NoteDatabase::class.java,"note-database").fallbackToDestructiveMigration().build()
        }

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabaseInstance(context: Context): NoteDatabase {
            if (INSTANCE ==null){
                synchronized(this){
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }
}