package com.example.a2024aswgr2asjc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class
MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fun mostrarSnackbar(texto: String) {
            val snackbar = Snackbar.make(
                findViewById(R.id.id_layout_main),
                texto,
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.show()
        }

        val callbackContenidoIntentExplicito = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result ->
            if (result.resultCode == Activity.RESULT_OK){
                if (result.data != null){
                    val data = result.data
                    mostrarSnackbar("${data?.getStringExtra("nombreModificado")}")
                }
            }
        }

        val callbackContenidoIntentImplicito = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result ->
            if (result.resultCode == Activity.RESULT_OK){
                if (result.data != null){
                    if (result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(uri, null, null, null, null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono: $telefono")
                    }
                }
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonACicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonACicloVida.setOnClickListener {
            irActividad(ACicloVida::class.java)
        }

        val botonBListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonBListView.setOnClickListener {
            irActividad(BListView::class.java)
        }

        val botonIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackContenidoIntentImplicito.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito
        )
        botonIntentExplicito.setOnClickListener {
            val intentExplicito = Intent(
                this,
                CIntentExplicitoParametros::class.java
            )
            intentExplicito.putExtra("nombre", "Adrian")
            intentExplicito.putExtra("apellido", "Eguez")
            intentExplicito.putExtra("edad", 29)
            intentExplicito.putExtra("entrenador", BEntrenador(1, "Adrian", null))
            callbackContenidoIntentExplicito.launch(intentExplicito)
        }
    }

    fun irActividad (
        clase: Class<*>
    ) {
        val intent = Intent(this,clase)
        startActivity(intent)
    }


}