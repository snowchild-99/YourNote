package com.snowchild.yourNote.database

import androidx.lifecycle.LiveData
import com.snowchild.yourNote.model.NotesData

class NotesRepository(private val noteDao: NoteDao) {

    val allNotesData : LiveData<List<NotesData>> = noteDao.getAllNotes()

    suspend fun insertNotes(notesData : NotesData) {
        noteDao.addNote(notesData)
    }

    suspend fun deleteNotes(notesData: NotesData){
        noteDao.deleteNote(notesData)
    }


    suspend fun updateNotes(notesData: NotesData) {
        noteDao.updateNote(notesData.id,notesData.title,notesData.notes)
    }


}