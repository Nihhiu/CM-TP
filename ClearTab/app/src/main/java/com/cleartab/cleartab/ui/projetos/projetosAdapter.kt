package com.cleartab.cleartab.ui.projetos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cleartab.cleartab.R
import com.cleartab.cleartab.retrofit.tables.Projeto

class projetosAdapter (private val myDataset: List<Projeto>, private val onItemClick: (Projeto) -> Unit) :
    RecyclerView.Adapter<projetosAdapter.MyViewHolder>() {

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): projetosAdapter.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.projetos, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(textView)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val projeto = myDataset[position]
        holder.textView.text = projeto.nome
        holder.textView.setOnClickListener { onItemClick(projeto) }
    }

    override fun getItemCount() = myDataset.size
}