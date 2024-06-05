package alexandre.cavaleiro.tasklist.adapter

import alexandre.cavaleiro.tasklist.R
import alexandre.cavaleiro.tasklist.model.Task
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TaskAdapter(context: Context, private val taskList: MutableList<Task>
): ArrayAdapter<Task>(context, R.layout.tile_task, taskList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = taskList[position]
        var flag = "TODO"

        if (task.completa){
            flag = "completa"
        }else{
            flag = "Todo"
        }

        var contactTileView = convertView
        if(contactTileView == null){
            contactTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as
                    LayoutInflater).inflate(R.layout.tile_task, parent, false)
        }


        contactTileView!!.findViewById<TextView>(R.id.descTaskEt).setText(task.descricao)
        contactTileView!!.findViewById<TextView>(R.id.completaTV).setText(flag)


        return contactTileView
    }
}