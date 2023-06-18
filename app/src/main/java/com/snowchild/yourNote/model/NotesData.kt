package com.snowchild.yourNote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notesTable")
data class NotesData(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "notes_title")
    val title: String?,

    @ColumnInfo(name = "actual_note")
    val notes: String?,

    @ColumnInfo(name = "data")
    val date: String?,


) : Serializable
