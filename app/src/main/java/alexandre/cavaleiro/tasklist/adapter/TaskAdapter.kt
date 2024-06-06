package alexandre.cavaleiro.tasklist.adapter

import alexandre.cavaleiro.tasklist.R
import alexandre.cavaleiro.tasklist.databinding.TileTaskBinding
import alexandre.cavaleiro.tasklist.model.Task
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TaskAdapter(context: Context, private val taskList: MutableList<Task>
): ArrayAdapter<Task>(context, R.layout.tile_task, taskList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = taskList[position]

        var ttb: TileTaskBinding? = null

        var taskTileView = convertView
        if (taskTileView == null) {
            ttb = TileTaskBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent, false
            )
            taskTileView = ttb.root

            val tileTaskHolder = TileTaskHolder(ttb.descricaoTV)
            taskTileView.tag = tileTaskHolder
        }

        val holder = taskTileView.tag as TileTaskHolder
        holder.descricaoTv.setText(task.descricao)
        //holder.completeCb.setText(flag)

 //falta algo

        if (task.completa){
            holder.descricaoTv.apply { paintFlags=paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
        }else{ holder.descricaoTv.apply { paintFlags=paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv() } }

        return taskTileView
    }

    private class TileTaskHolder(val descricaoTv: TextView)


}