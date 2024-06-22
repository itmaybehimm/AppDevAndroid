package com.example.appdevsneha

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile


@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDao



    companion object{
        private fun buildDatabase(context: Context):NoteDatabase{
            return Room.databaseBuilder(context,NoteDatabase::class.java,"note-database").build()
        }

        @Volatile
        private var INSTANCE:NoteDatabase? = null

        fun getDatabaseInstance(context: Context):NoteDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }
}