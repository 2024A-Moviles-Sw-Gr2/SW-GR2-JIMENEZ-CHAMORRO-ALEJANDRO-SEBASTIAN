package com.example.a2024aswgr2asjc

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blist_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_blist_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.lv_list_view)

        // Manejar la lógica de cómo se maneja la interfaz
        // Necesita: contexto de dónde estamos trabajando, un xml para el layout, arreglo
        val adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // Nombre de la vista
            arreglo // Arreglo
        )
        // Setear el adaptador
        listView.adapter = adaptador
        // Actualiza el UI, si no se pone no se ven cambios en la interfaz
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(
            R.id.btn_aniadir_list_view
        )

        botonAnadirListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }
        registerForContextMenu(listView)
    }

    var posicionItemSeleccionado = 0

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                return true
            }

            R.id.mi_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                abrirDiálogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDiálogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar")
        builder.setPositiveButton("Si") { _, which ->
            mostrarSnackbar("Acepto $which")
        }
        builder.setNegativeButton("No", null)
        val opciones = resources.getStringArray(R.array.string_array_opciones)
        val selecciónPrevia = booleanArrayOf(
            true,
            false,
            false
        )

        builder.setMultiChoiceItems(
            opciones,
            selecciónPrevia
        ) { _, which, _ ->
            mostrarSnackbar("Selecciono $which")
        }

        val diálogo = builder.create()
        diálogo.show()
    }

    fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>) {
        arreglo.add(
            BEntrenador(
                4, "Entrenador 4", "Descripcion 4"
            )
        )
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_blist_view),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}