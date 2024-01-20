package com.blez.myapplication.feature_note.domain.model

import com.blez.myapplication.feature_note.domain.util.NoteOrder
import com.blez.myapplication.feature_note.domain.util.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val noteOrder : NoteOrder  = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false
)
