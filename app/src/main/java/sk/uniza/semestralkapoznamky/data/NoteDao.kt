package sk.uniza.semestralkapoznamky.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    /**
     * Prida zadanu poznamku
     *
     * @param note
     */
    @Insert
    suspend fun insert(note: Note)

    /**
     * Vymaze vsetky poznamky
     *
     */
    @Query("DELETE FROM note")
    suspend fun deleteAll()

    /**
     * Vrati poznamku podla zadaneho ID
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM note WHERE id=:id")
    fun getSelectedNote(id: Int): Flow<Note>

    /**
     * Updatne poznamku
     */
    @Update
    suspend fun update(note: Note)

    /**
     * Vymaze poznamku podla zadaneho ID
     *
     * @param id
     */
    @Query("DELETE FROM note WHERE id=:id")
    suspend fun delete(id: Int)

    /**
     * Vyhlada poznamky a zoradi podla pinu zo zadaneho textu v searchbare
     *
     * @param searchQuery
     * @return
     */
    @Query("Select * from note WHERE name LIKE :searchQuery ORDER BY pinned DESC")
    fun searchDatabase(searchQuery: String): Flow<List<Note>>

}