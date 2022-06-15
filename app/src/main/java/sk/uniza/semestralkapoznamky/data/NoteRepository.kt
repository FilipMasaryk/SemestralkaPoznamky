package sk.uniza.semestralkapoznamky.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    /**
     * Pridanie poznamky do databazy
     *
     * @param note
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    /**
     * Updatnutie poznamky v databaze
     *
     * @param note
     */
    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    /**
     * Vymazanie poznamky z databazy podla zadaneho ID
     *
     * @param id
     */
    @WorkerThread
    suspend fun delete(id: Int) {
        noteDao.delete(id)
    }

    /**
     * Vrati poznamku z databazy podla zadaneho ID
     *
     * @param id
     * @return
     */
    fun getSelectedNote(id: Int): Flow<Note> {
        return noteDao.getSelectedNote(id)
    }

    /**
     * Vyhlada poznamky podla textu v searchbare
     *
     * @param searchQuery
     * @return
     */
    fun searchDatabase(searchQuery: String): Flow<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }
}