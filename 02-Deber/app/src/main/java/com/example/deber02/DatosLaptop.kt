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

class DatosLaptop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_laptop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_datos_laptop)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val modelo = findViewById<EditText>(R.id.inpt_modelo_laptop)
        val anio = findViewById<EditText>(R.id.inpt_anio_laptop)
        val precio = findViewById<EditText>(R.id.inpt_precio_laptop)
        val fabricaId = findViewById<EditText>(R.id.inpt_id_fabrica)
        val btnDatosFabrica = findViewById<Button>(R.id.btn_datos_laptop)

        val laptopSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("laptopSeleccionada", Laptop::class.java)
        } else {
            intent.getParcelableExtra<Laptop>("laptopSeleccionada")
        }

        val fabricaSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("fabricaSeleccionada", Fabrica::class.java)
        } else {
            intent.getParcelableExtra<Fabrica>("fabricaSeleccionada")
        }

        val crear = intent.getBooleanExtra("crear", true)

        if (crear) {
            fabricaId.setText(fabricaSeleccionada!!.id.toString())
            btnDatosFabrica.setOnClickListener {
                crearLaptop(modelo, anio, precio, fabricaId)
                goToActivity(ListaDeLaptops::class.java, fabricaSeleccionada)
            }
        } else {
            llenarDatosLaptop(laptopSeleccionada!!, modelo, anio, precio, fabricaId)
            btnDatosFabrica.setOnClickListener {
                actualizarLaptop(laptopSeleccionada.id, modelo, anio, precio, fabricaId)
                goToActivity(ListaDeLaptops::class.java, fabricaSeleccionada!!)
            }
        }
    }

    private fun crearLaptop(
        modelo: EditText,
        anio: EditText,
        precio: EditText,
        fabricaId: EditText
    ) {
        BaseDatosMemoriaFabrica.tables!!.crearLaptop(
            modelo.text.toString(),
            anio.text.toString().toInt(),
            precio.text.toString().toFloat(),
            fabricaId.text.toString().toInt()
        )
    }

    private fun actualizarLaptop(
        id: Int,
        modelo: EditText,
        anio: EditText,
        precio: EditText,
        fabricaId: EditText
    ) {
        BaseDatosMemoriaFabrica.tables!!.actualizarLaptop(
            id,
            modelo.text.toString(),
            anio.text.toString().toInt(),
            precio.text.toString().toFloat(),
            fabricaId.text.toString().toInt()
        )
    }

    private fun llenarDatosLaptop(
        laptop: Laptop,
        modelo: EditText,
        anio: EditText,
        precio: EditText,
        fabricaId: EditText
    ) {
        modelo.setText(laptop.modelo)
        anio.setText(laptop.anio.toString())
        precio.setText(laptop.precio.toString())
        fabricaId.setText(laptop.fabrica_id.toString())
    }

    private fun goToActivity(
        activityClass: Class<*>,
        datosFabrica: Fabrica
    ) {
        val intent = Intent(this, activityClass)
        intent.apply {
            putExtra("fabricaSeleccionada", datosFabrica)
        }
        startActivity(intent)
    }
}
