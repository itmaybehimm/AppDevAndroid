package com.example.appdevsneha.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appdevsneha.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class, Folder::class, Quiz::class, Answer::class, Question::class],
    version = 6
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabaseInstance(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note-database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class NoteDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.quizDao(), database.questionDao(), database.answerDao())
                }
            }
        }

        suspend fun populateDatabase(quizDao: QuizDao, questionDao: QuestionDao, answerDao: AnswerDao) {

            val questions = listOf(
                Question( question = "What is the fastest land animal?", optionA = "Lion", optionB = "Cheetah", optionC = "Tiger", optionD = "Leopard", answer = 2),
                Question( question = "Which animal is known as the king of the jungle?", optionA = "Elephant", optionB = "Tiger", optionC = "Lion", optionD = "Leopard", answer = 3),
                Question( question = "What is the largest mammal?", optionA = "Elephant", optionB = "Blue Whale", optionC = "Giraffe", optionD = "Hippopotamus", answer = 2),
                Question( question = "Which bird is known for its impressive tail feathers?", optionA = "Peacock", optionB = "Parrot", optionC = "Eagle", optionD = "Penguin", answer = 1),
                Question( question = "Which animal is known for its ability to change color?", optionA = "Frog", optionB = "Octopus", optionC = "Chameleon", optionD = "Salamander", answer = 3)
            )

            questions.forEach { question ->
                questionDao.insertQuestion(question)
            }
        }
    }
}
