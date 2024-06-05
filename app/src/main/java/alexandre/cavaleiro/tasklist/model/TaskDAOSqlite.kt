package alexandre.cavaleiro.tasklist.model

import alexandre.cavaleiro.tasklist.R
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class TaskDAOSqlite(context: Context):TaskDAO {
    companion object CompanionTask{
        private const val TASK_DATABASE_FILE = "tasks"
        private const val TASK_TABLE = "task"
        private const val ID_COLUMN = "id"
        private const val DESC_COLUMN = "descricao"
        private const val COMPLETE_COLUMN = "completa"
        private const val CREATE_TASK_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $TASK_TABLE (" +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$DESC_COLUMN TEXT NOT NULL, " +
                    "$COMPLETE_COLUMN BOOLEAN NOT NULL " +
                    ");"
    }

    private val taskSqliteDatabase: SQLiteDatabase
    init {
        taskSqliteDatabase =
            context.openOrCreateDatabase(TASK_DATABASE_FILE, MODE_PRIVATE, null)
        try {
            taskSqliteDatabase.execSQL(CREATE_TASK_TABLE_STATEMENT)
        }catch (se: SQLException){
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }

    override fun createTask(task: Task) = taskSqliteDatabase.insert(
        TASK_TABLE,
        null,
        task.toContentValues()
    ).toInt()


    override fun retrieveTask(id: Int): Task? {
        val cursor = taskSqliteDatabase.rawQuery(
            "SELECT * FROM $TASK_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val task = if (cursor.moveToFirst()) cursor.rowToTask() else null
        cursor.close()
        return task
    }

    override fun retrieveTasks(): MutableList<Task> {
        val taskList = mutableListOf<Task>()
        val cursor = taskSqliteDatabase.rawQuery(
            "SELECT * FROM $TASK_TABLE ORDER BY $DESC_COLUMN",
            null
        )

        while (cursor.moveToNext()){
            taskList.add(cursor.rowToTask())
        }
        cursor.close()
        return taskList
    }

    override fun updateTask(task: Task): Int = taskSqliteDatabase.update(
        TASK_TABLE,
        task.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(task.id.toString())
    )

    override fun deleteTask(id: Int): Int =taskSqliteDatabase.delete(
        TASK_TABLE,
        "$ID_COLUMN = ?",
        arrayOf(id.toString())

    )

    private fun Cursor.rowToTask(): Task {
        val complete = getInt(getColumnIndexOrThrow(COMPLETE_COLUMN)) != 0
        return Task(
            getInt(getColumnIndexOrThrow(ID_COLUMN)),
            getString(getColumnIndexOrThrow(DESC_COLUMN)),
            complete
        )
    }
    private fun Task.toContentValues(): ContentValues = with(ContentValues()){
        put(DESC_COLUMN, descricao)
        put(COMPLETE_COLUMN, completa)
        this
    }
}