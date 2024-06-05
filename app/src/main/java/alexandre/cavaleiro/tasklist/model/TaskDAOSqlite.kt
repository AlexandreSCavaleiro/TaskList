package alexandre.cavaleiro.tasklist.model

import alexandre.cavaleiro.tasklist.R
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
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
                    "$DESC_COLUMN TEXT NOT NULL, "+
                    "$COMPLETE_COLUMN BOOLEAN NOT NULL, "+
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

    override fun createTask(task: Task): Int {
        val cv = ContentValues()
        cv.put(DESC_COLUMN, task.descricao)
        cv.put(COMPLETE_COLUMN, task.completa)

        return taskSqliteDatabase.insert(TASK_TABLE, null, cv).toInt()
    }

    override fun retrieveTask(id: Int): Task {
        TODO("Not yet implemented")
    }

    override fun retrieveTasks(): MutableList<Task> {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task): Int {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Int): Int {
        TODO("Not yet implemented")
    }


}