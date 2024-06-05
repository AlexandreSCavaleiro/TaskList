package alexandre.cavaleiro.tasklist.view

import alexandre.cavaleiro.tasklist.R
import alexandre.cavaleiro.tasklist.databinding.ActivityMainBinding
import alexandre.cavaleiro.tasklist.model.Constant.EXTRA_TASK
import alexandre.cavaleiro.tasklist.model.Task
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
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
    private val taskAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            taskList.map { task ->
                task.descricao
            }
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillTasks()
        amb.taskslv.adapter = taskAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if (result.resultCode == RESULT_OK){
                val task =result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                task?.let { _task ->
                    taskList.add(_task)
                    taskAdapter.add(_task.descricao)
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }
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

    /*
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
    */
}