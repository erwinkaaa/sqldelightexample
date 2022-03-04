package id.wendei.sqldelightexample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.Note
import id.wendei.sqldelightexample.data.NoteDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource
) : ViewModel() {

    val notes = noteDataSource.getNote()

    var noteDetail by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    fun onTitleChange(value: String) {
        title = value
    }

    fun onContentChange(value: String) {
        content = value
    }

    fun insertNote() {
        if (title.isEmpty() || content.isEmpty()) {
            return
        }
        viewModelScope.launch {
            noteDataSource.insertNote(title = title, content = content)
            title = ""
            content = ""
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNote(id = id)
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.getNoteById(id = id)
        }
    }

}