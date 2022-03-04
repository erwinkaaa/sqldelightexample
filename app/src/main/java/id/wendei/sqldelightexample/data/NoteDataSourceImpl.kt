package id.wendei.sqldelightexample.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import example.Note
import id.wendei.sqldelightexample.SqlDelightExampleDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NoteDataSourceImpl(
    db: SqlDelightExampleDatabase
): NoteDataSource, CoroutineScope {

    private val queries = db.noteQueries

    /*
    executeAsOneOrNull() -> case when return is nullable
    executeAsOne() -> force to not nullable
     */

    override suspend fun getNoteById(id: Long): Note? {
        return withContext(coroutineContext) {
            queries.getNoteById(id).executeAsOneOrNull()
        }
    }

    override fun getNote(): Flow<List<Note>> {
        return queries.getNote().asFlow().mapToList()
    }

    override suspend fun deleteNote(id: Long) {
        withContext(coroutineContext) {
            queries.deleteNote(id)
        }
    }

    override suspend fun insertNote(title: String, content: String, id: Long?) {
        withContext(coroutineContext) {
            queries.insertNote(id = id, title = title, content = content)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}