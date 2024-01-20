package com.blez.myapplication.feature_note.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blez.myapplication.feature_note.data.dao.NoteDao
import com.blez.myapplication.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao : NoteDao
    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}