package sk.uniza.semestralkapoznamky.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.uniza.semestralkapoznamky.data.Note
import sk.uniza.semestralkapoznamky.data.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteEditorModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
}