package com.example.deber003

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OpcionesAdapter(private val opciones: List<String>) :
    RecyclerView.Adapter<OpcionesAdapter.OpcionesViewHolder>() {

    inner class OpcionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textOverlay: TextView = view.findViewById(R.id.text_overlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpcionesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_botones_tipo_cuenta, parent, false)
        return OpcionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpcionesViewHolder, position: Int) {
        val opcion = opciones[position]
        holder.textOverlay.text = opcion
    }

    override fun getItemCount() = opciones.size
}
