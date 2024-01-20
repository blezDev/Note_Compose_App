package com.blez.myapplication.feature_note.data.repository

import com.blez.myapplication.feature_note.data.dao.NoteDao
import com.blez.myapplication.feature_note.domain.model.Note
import com.blez.myapplication.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao : NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)
    }

    override suspend fun createNote(note: Note) {
        dao.createNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}