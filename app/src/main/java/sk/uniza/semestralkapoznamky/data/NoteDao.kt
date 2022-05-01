package sk.uniza.semestralkapoznamky.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY name ASC")
    fun getAlphabetizedNotes(): Flow<List<Note>>

    @Insert()
    suspend fun insert(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}