package com.snowchild.yourNote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.snowchild.yourNote.model.NotesData


@Database (entities = [NotesData::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {

        @Volatile
        private var notesDatabaseInstance: NoteDatabase? = null
        private const val DATABASE_NAME = "notes_database"
        fun getDatabaseInstance(context: Context): NoteDatabase {
            return notesDatabaseInstance?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                notesDatabaseInstance = instance
                instance
            }

        }
    }


}