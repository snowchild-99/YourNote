package com.snowchild.yourNote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.snowchild.yourNote.model.NotesData


@Dao
interface NoteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notes : NotesData)

    @Delete
    suspend fun deleteNote(notes: NotesData)

    @Query(" SELECT * from notesTable ORDER BY id ASC")
    fun getAllNotes() : LiveData<List<NotesData>>

    @Query("UPDATE notesTable Set notes_title = :title, actual_note = :note WHERE id = :id ")
    suspend fun updateNote(id : Int? , title : String? , note : String?)



}