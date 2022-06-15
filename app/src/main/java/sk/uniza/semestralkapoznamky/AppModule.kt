package sk.uniza.semestralkapoznamky

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sk.uniza.semestralkapoznamky.data.NoteDao
import sk.uniza.semestralkapoznamky.data.NoteRepository
import sk.uniza.semestralkapoznamky.data.NoteRoomDatabase
import javax.inject.Singleton

/**
 * Modul, ktory ma definovane ako vytvarat instancie jednotlivych tried(dependencies)
 * Tieto instancie su vytvarane ako singleton
 *
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Singleton
    @Provides
    fun provideNoteDao(
        noteRoomDatabase: NoteRoomDatabase
    ): NoteDao {
        return noteRoomDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ): NoteRoomDatabase {
        return Room.databaseBuilder(
            app,
            NoteRoomDatabase::class.java,
            "note_database"
        ).build()
    }
}