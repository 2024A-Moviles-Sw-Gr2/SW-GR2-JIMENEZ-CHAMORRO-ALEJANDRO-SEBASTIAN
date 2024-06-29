package com.example.a2024aswgr2asjc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""

    fun mostrarSnackBar(texto: String) {
        textoGlobal += texto
        val snack = Snackbar.make(
            findViewById(
                R.id.cl_ciclo_vida
            ),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mostrarSnackBar("Cadena")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackBar("OnStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackBar("OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackBar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackBar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackBar("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackBar("OnDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("textoGlobal", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val textoRecuperadoDeVariable: String? = savedInstanceState.getString("textoGlobal")
        if (textoRecuperadoDeVariable != null) {
            mostrarSnackBar(textoRecuperadoDeVariable)
            textoGlobal = textoRecuperadoDeVariable
        }
    }
}