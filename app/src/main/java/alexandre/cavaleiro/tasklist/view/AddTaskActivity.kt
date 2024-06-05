package alexandre.cavaleiro.tasklist.view
import alexandre.cavaleiro.tasklist.R
import alexandre.cavaleiro.tasklist.databinding.ActivityAddTaskBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private val atab : ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)


        atab.addNewTaskBt.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {

        }
}