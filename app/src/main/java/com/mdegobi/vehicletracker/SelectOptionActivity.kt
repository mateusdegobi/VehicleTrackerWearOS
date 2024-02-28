package com.mdegobi.vehicletracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectOptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_option)

        val value = intent.getStringExtra("value")

        val infoText = findViewById<TextView>(R.id.textView)

        infoText.text = value

        val myList = listOf("Lubrificar corrente", "Completar óleo", "Trocar óleo", "Trocar peça", "Revisão")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = OptionsListAdapter(myList, object : OptionsListAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {
                val intent = Intent(this@SelectOptionActivity, ConfirmPushActivity::class.java).apply {
                    putExtra("type", item)
                    putExtra("value", value)
                    Log.d("InfoAdapter", "Item: $item")
                }
                startActivity(intent)
            }
        })


    }
}


class OptionsListAdapter(private val dataList: List<String>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<OptionsListAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    class ViewHolder(view: View, itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_text)

        init {
            view.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view) {position ->
            val item = dataList[position]
            itemClickListener.onItemClick(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textView.text = item
    }

    override fun getItemCount() = dataList.size

}
