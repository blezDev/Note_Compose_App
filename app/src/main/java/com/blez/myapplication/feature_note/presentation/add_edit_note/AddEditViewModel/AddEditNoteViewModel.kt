package com.blez.myapplication.feature_note.presentation.add_edit_note.AddEditViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.myapplication.feature_note.domain.model.InvalidNoteException
import com.blez.myapplication.feature_note.domain.model.Note
import com.blez.myapplication.feature_note.domain.use_case.NoteUseCases
import com.blez.myapplication.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.blez.myapplication.feature_note.presentation.add_edit_note.NoteTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val _title = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val title: State<NoteTextFieldState>
        get() = _title

    private val _content = mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
    val content: State<NoteTextFieldState>
        get() = _content

    private val _color = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val color: State<Int>
        get() = _color

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow  = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
       savedStateHandle.get<Int>("noteId")?.let {noteId->
           if (noteId!= -1){
              viewModelScope.launch {
                  noteUseCases.getNote(noteId)?.also { note->
                      currentNoteId = note.id
                      _title.value = title.value.copy(
                          text = note.title,
                          isHintVisible = false
                      )
                      _content.value = content.value.copy(
                          text = note.content,
                          isHintVisible = false
                      )
                      _color.value = note.color

                  }
              }
           }
       }
    }
 


    fun onEvent(event : AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.ChangeColor -> {
                _color.value = event.color
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _content.value = _content.value.copy(isHintVisible = !event.focusState.isFocused && content.value.text.isBlank())
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(isHintVisible = !event.focusState.isFocused && title.value.text.isBlank())
            }
            is AddEditNoteEvent.EnteredContent -> {
                _content.value = _content.value.copy(text = event.value)
            }
            is AddEditNoteEvent.EnteredTitle -> {
               _title.value = title.value.copy(
                   text = event.value
               )
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.createNote(Note(
                            title = title.value.text,
                            content = content.value.text,
                            timeStamp = System.currentTimeMillis(),
                            color = color.value,
                            id = currentNoteId
                        ))
                        _eventFlow.emit(UIEvent.SaveNote)
                    }catch (e : InvalidNoteException){
                        _eventFlow.emit(UIEvent.ShowSnackbar(message = e.message ?: "Couldn't save note"))
                    }
                }
            }
        }
    }


    sealed class UIEvent{
        data class ShowSnackbar(val message : String) : UIEvent()
        object SaveNote : UIEvent()
    }


}