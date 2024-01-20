package com.blez.myapplication.feature_note.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blez.myapplication.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes() : Flow<List<Note>>
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id : Int) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNote(note : Note)

    @Delete
    suspend fun deleteNote(note: Note)



}