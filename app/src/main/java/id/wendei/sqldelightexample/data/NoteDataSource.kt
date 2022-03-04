package id.wendei.sqldelightexample.data

import example.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun getNoteById(id: Long): Note?
    fun getNote(): Flow<List<Note>>
    suspend fun deleteNote(id: Long)
    suspend fun insertNote(title: String, content: String, id: Long? = null)
}