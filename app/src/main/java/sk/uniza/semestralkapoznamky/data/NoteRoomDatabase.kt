package sk.uniza.semestralkapoznamky.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}