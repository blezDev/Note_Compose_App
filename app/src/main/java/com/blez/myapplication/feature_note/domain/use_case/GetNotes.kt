package com.blez.myapplication.feature_note.domain.use_case

import com.blez.myapplication.feature_note.domain.model.Note
import com.blez.myapplication.feature_note.domain.repository.NoteRepository
import com.blez.myapplication.feature_note.domain.util.NoteOrder
import com.blez.myapplication.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {
    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map {
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> {
                            it.sortedBy { it.title.lowercase() }
                        }

                        is NoteOrder.Date -> {
                            it.sortedBy { it.timeStamp }
                        }

                        is NoteOrder.Color -> {
                            it.sortedBy { it.color }
                        }


                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> {
                            it.sortedByDescending { it.title.lowercase() }
                        }

                        is NoteOrder.Date -> {
                            it.sortedByDescending { it.timeStamp }
                        }

                        is NoteOrder.Color -> {
                            it.sortedByDescending { it.color }
                        }
                    }
                }

                else -> it
            }
        }

    }
}