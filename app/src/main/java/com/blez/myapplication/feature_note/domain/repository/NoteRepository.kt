package com.blez.myapplication.feature_note.domain.repository

import com.blez.myapplication.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id : Int) : Note?

    suspend fun createNote(note: Note)

    suspend fun deleteNote(note: Note)
}