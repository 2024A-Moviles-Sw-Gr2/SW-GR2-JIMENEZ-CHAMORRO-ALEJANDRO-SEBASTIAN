package com.example.deber003

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccionesAdapter(private val acciones: List<Accion>) :
    RecyclerView.Adapter<AccionesAdapter.AccionesViewHolder>() {

    class AccionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.item_image)
        val itemText: TextView = view.findViewById(R.id.item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccionesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_botones_acciones, parent, false)
        return AccionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccionesViewHolder, position: Int) {
        val accion = acciones[position]
        holder.itemText.text = accion.nombre
        holder.itemImage.setImageResource(accion.icono)
    }

    override fun getItemCount() = acciones.size
}
