package com.example.a2024aswgr2asjc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,                    //Actividad que estamos recibiendo
    private val lista: ArrayList<BEntrenador>,  //Lista de entrenadores
    private val recyclerView: RecyclerView                  //Referencia al recycler view
): RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>() {

    inner class MyViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val descripcionTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numeroLikes = 0

        // Se va a agarrar cada uno de los componentes visuales y se van a guardar en las variables
        // respectivas
        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            descripcionTextView = view.findViewById(R.id.tv_descripcion)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton = view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener {
                anadirLikes()
            }
        }

        fun anadirLikes() {
            numeroLikes += 1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
    }

    // Buscar el xml y setearle
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            // Se busca el layout del recycle view
            .inflate(R.layout.recycler_view_vista, parent, false)

        return MyViewHolder(itemView)
    }

    // La lista del parámetro enviado es la que nos va a dar el número de elementos
    override fun getItemCount(): Int {
        return this.lista.size
    }

    // Lógica para llenar lo visual con datos
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text = entrenadorActual.nombre
        holder.accionButton.text = "ID: $entrenadorActual.id" + "Nombre: ${entrenadorActual.nombre}"
    }
}