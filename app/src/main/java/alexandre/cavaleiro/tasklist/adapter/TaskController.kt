package alexandre.cavaleiro.tasklist.adapter

import alexandre.cavaleiro.tasklist.model.Task
import alexandre.cavaleiro.tasklist.model.TaskDAO
import alexandre.cavaleiro.tasklist.model.TaskDAOSqlite
import alexandre.cavaleiro.tasklist.view.MainActivity

class TaskController(mainActivity: MainActivity) {
    private val taskDaoImpl: TaskDAO = TaskDAOSqlite(mainActivity)

    fun insertTask(task: Task): Int = taskDaoImpl.createTask(task)
    fun getTask(id: Int) = taskDaoImpl.retrieveTask(id)
    fun getTasks() = taskDaoImpl.retrieveTasks()
    fun editTask(task: Task) = taskDaoImpl.updateTask(task)
    fun removeTask(id: Int) = taskDaoImpl.deleteTask(id)
}