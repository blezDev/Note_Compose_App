package com.blez.myapplication.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blez.myapplication.ui.theme.BabyBlue
import com.blez.myapplication.ui.theme.LightGreen
import com.blez.myapplication.ui.theme.RedOrange
import com.blez.myapplication.ui.theme.RedPink
import com.blez.myapplication.ui.theme.Violet

@Entity
data class Note(
    val title : String,
    val content : String,
    val timeStamp : Long,
    val color : Int,
@PrimaryKey var id : Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}


class InvalidNoteException(message : String) : Exception(message)