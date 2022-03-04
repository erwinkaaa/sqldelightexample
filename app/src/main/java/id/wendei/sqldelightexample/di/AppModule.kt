package id.wendei.sqldelightexample.di

import android.app.Application
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.wendei.sqldelightexample.SqlDelightExampleDatabase
import id.wendei.sqldelightexample.data.NoteDataSource
import id.wendei.sqldelightexample.data.NoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDelightDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = SqlDelightExampleDatabase.Schema,
            context = app,
            name = "sqldelightexample.db",
            callback = object : AndroidSqliteDriver.Callback(SqlDelightExampleDatabase.Schema) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    super.onConfigure(db)
                    setPragma(db, "JOURNAL_MODE = WAL")
                    setPragma(db, "SYNCHRONOUS = 2")
                }

                private fun setPragma( db: SupportSQLiteDatabase, pragma: String) {
                    val cursor = db.query("PRAGMA $pragma")
                    cursor.moveToFirst()
                    cursor.close()
                }
            }
        )
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(sqlDriver: SqlDriver): NoteDataSource {
        return NoteDataSourceImpl(SqlDelightExampleDatabase(sqlDriver))
    }

}