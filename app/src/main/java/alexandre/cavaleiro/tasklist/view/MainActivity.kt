package alexandre.cavaleiro.tasklist.view

import alexandre.cavaleiro.tasklist.R
import alexandre.cavaleiro.tasklist.adapter.TaskAdapter
import alexandre.cavaleiro.tasklist.databinding.ActivityMainBinding
import alexandre.cavaleiro.tasklist.model.Constant.EXTRA_TASK
import alexandre.cavaleiro.tasklist.model.Task
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //view
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //ARL
    private lateinit var carl: ActivityResultLauncher<Intent>

    //Data Source
    private val taskList: MutableList<Task> = mutableListOf()

    //Adapter
    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter( this, taskList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillTasks()

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle="Tasks"

        amb.taskslv.adapter = taskAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if (result.resultCode == RESULT_OK){
                val task = result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                task?.let { _task ->
                    if (taskList.any{ it.id == task.id }){
                        val position = taskList.indexOfFirst { it.id == task.id }
                        taskList[position] = _task
                    }else {
                        taskList.add(_task)
                    }
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.taskslv)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId){
            R.id.removeTaskMi -> {
                taskList.removeAt(position)
                taskAdapter.notifyDataSetChanged()
                Toast.makeText(this,"Removido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editTaskMi -> {
                val task = taskList[position]
                val editTaskIntent = Intent(this, AddTaskActivity::class.java)
                editTaskIntent.putExtra(EXTRA_TASK, task)
                carl.launch(editTaskIntent)
                true
            }
            else -> {true}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.taskslv)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addTaskMi -> {
                carl.launch(Intent(this,AddTaskActivity::class.java))
                true
            }
            else -> true
        }
    }


    private fun fillTasks(){
        for (i in 1..50){
            taskList.add(
                Task(
                    i,
                    "task $i",
                    false
                )
            )
        }
    }

}