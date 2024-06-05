package alexandre.cavaleiro.tasklist.model

interface TaskDAO {
    fun createTask(task: Task): Int
    fun retrieveTask(id: Int): Task
    fun retrieveTasks(): MutableList<Task>
    fun updateTask(task: Task): Int
    fun deleteTask(id: Int): Int
}