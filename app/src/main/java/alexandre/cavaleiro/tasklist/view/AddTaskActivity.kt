package alexandre.cavaleiro.tasklist.view
import alexandre.cavaleiro.tasklist.databinding.ActivityAddTaskBinding
import alexandre.cavaleiro.tasklist.model.Constant.EXTRA_TASK
import alexandre.cavaleiro.tasklist.model.Constant.INVALID_CONTACT_ID
import alexandre.cavaleiro.tasklist.model.Constant.VIEW_TASK
import alexandre.cavaleiro.tasklist.model.Task
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private val atab : ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(atab.root)

        setSupportActionBar(atab.toolbarIn.toolbar)
        supportActionBar?.subtitle="Tasks"

        val receivedTask = intent.getParcelableExtra<Task>(EXTRA_TASK)
        receivedTask?.let { _receivedTask ->
            val viewTask: Boolean = intent.getBooleanExtra(VIEW_TASK,false)
            with(atab) {
                if (viewTask) {
                    descTaskEt.isEnabled = false
                    completeCb.isEnabled = false
                    addNewTaskBt.visibility= View.GONE
                }
                descTaskEt.setText(_receivedTask.descricao)
                completeCb.isChecked = _receivedTask.completa
            }

        }

        with(atab){
            addNewTaskBt.setOnClickListener{

                val task: Task = Task(
                    id = receivedTask?.id?: INVALID_CONTACT_ID,
                    descricao = descTaskEt.text.toString(),
                    completa = completeCb.isChecked
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_TASK, task)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
    //private fun generateId(): Int = Random(System.currentTimeMillis()).nextInt()
}