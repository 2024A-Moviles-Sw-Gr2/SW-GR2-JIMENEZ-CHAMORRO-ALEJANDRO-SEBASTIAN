package com.example.a2024aswgr2asjc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    fun mostrarSnackBar(texto:String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_sqlite),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ecrud_entrenador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_sqlite)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDeDatos.tablaEntreandor!!.consultarEntrenadorPorID(
                id.text.toString().toInt()
            )

            if (entrenador == null) {
                mostrarSnackBar("Usu. no encontrado")
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
            } else {
                id.setText(entrenador.id.toString())
                id.setText(entrenador.nombre)
                id.setText(entrenador.descripcion)
                mostrarSnackBar("Usu. encontrado")
            }
        }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntreandor!!.crearEntrenador(
                nombre.text.toString(),
                descripcion.text.toString()
            )

            if (respuesta) {
                mostrarSnackBar("Entrenador creado")
            }
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntreandor!!.actualizarEntrenadorFormulario(
                id.text.toString().toInt(),
                nombre.text.toString(),
                descripcion.text.toString()
            )

            if (respuesta) {
                mostrarSnackBar("Entrenador actualizado")
            }
        }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener{
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntreandor!!.eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            if(respuesta) mostrarSnackBar("Entr.Eliminado")
        }
    }
}