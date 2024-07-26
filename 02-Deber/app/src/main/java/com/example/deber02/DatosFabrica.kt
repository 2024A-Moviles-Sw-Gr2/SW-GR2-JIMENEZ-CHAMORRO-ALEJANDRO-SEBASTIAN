package com.example.deber02

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DatosFabrica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_fabrica)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_datos_fabrica)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre_fabrica = findViewById<EditText>(R.id.inpt_nombre_fabrica)
        val lugar_fabrica = findViewById<EditText>(R.id.inpt_lugar_fabrica)
        val fundacion_fabrica = findViewById<EditText>(R.id.inpt_fundacion_fabrica)
        val btnDatosFabrica = findViewById<Button>(R.id.btn_datos_fabrica)

        val fabricaSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("fabricaSeleccionada", Fabrica::class.java)
        } else {
            intent.getParcelableExtra<Fabrica>("fabricaSeleccionada")
        }

        if (fabricaSeleccionada == null) {
            btnDatosFabrica.setOnClickListener {
                crearFabrica(nombre_fabrica, lugar_fabrica, fundacion_fabrica)
                goToActivity(MainActivity::class.java)
            }
        } else {
            llenarDatosFabrica(
                fabricaSeleccionada,
                nombre_fabrica,
                lugar_fabrica,
                fundacion_fabrica
            )
            btnDatosFabrica.setOnClickListener {
                actualizarFabrica(
                    fabricaSeleccionada.id,
                    nombre_fabrica,
                    lugar_fabrica,
                    fundacion_fabrica
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun crearFabrica(nombre: EditText, lugar: EditText, fundacion: EditText) {
        BaseDatosMemoriaFabrica.tables!!.crearFabrica(
            nombre.text.toString(),
            lugar.text.toString(),
            fundacion.text.toString().toInt()
        )
    }

    private fun actualizarFabrica(id: Int, nombre: EditText, lugar: EditText, fundacion: EditText) {
        BaseDatosMemoriaFabrica.tables!!.actualizarFabrica(
            id,
            nombre.text.toString(),
            lugar.text.toString(),
            fundacion.text.toString().toInt()
        )
    }

    private fun llenarDatosFabrica(
        fabrica: Fabrica,
        nombre: EditText,
        lugar: EditText,
        fundacion: EditText
    ) {
        nombre.setText(fabrica.nombre)
        lugar.setText(fabrica.lugar)
        fundacion.setText(fabrica.año_fundacion.toString())
    }

    private fun goToActivity(
        activityClass: Class<*>
    ) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
