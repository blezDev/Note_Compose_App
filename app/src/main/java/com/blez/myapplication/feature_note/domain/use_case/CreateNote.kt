package com.blez.myapplication.feature_note.domain.use_case

import com.blez.myapplication.feature_note.domain.model.InvalidNoteException
import com.blez.myapplication.feature_note.domain.model.Note
import com.blez.myapplication.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class CreateNote(private val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of thenote can't be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of thenote can't be empty")
        }
            repository.createNote(note)
    }
}