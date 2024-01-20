package com.blez.myapplication.di

import android.app.Application
import androidx.room.Room
import com.blez.myapplication.feature_note.data.db.NoteDatabase
import com.blez.myapplication.feature_note.data.repository.NoteRepositoryImpl
import com.blez.myapplication.feature_note.domain.repository.NoteRepository
import com.blez.myapplication.feature_note.domain.use_case.CreateNote
import com.blez.myapplication.feature_note.domain.use_case.DeleteNote
import com.blez.myapplication.feature_note.domain.use_case.GetNote
import com.blez.myapplication.feature_note.domain.use_case.GetNotes
import com.blez.myapplication.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            DeleteNote(repository),
            CreateNote(repository),
            getNote = GetNote(repository)
        )
    }
}