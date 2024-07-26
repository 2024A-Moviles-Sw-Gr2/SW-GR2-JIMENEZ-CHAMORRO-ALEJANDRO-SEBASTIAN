package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var fabricas: ArrayList<Fabrica> = arrayListOf()
    private var adapter: ArrayAdapter<Fabrica>? = null
    private var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_fabricas_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        BaseDatosMemoriaFabrica.tables = SqliteHelper(this)
        val listaDeFabricas = findViewById<ListView>(R.id.lv_fabricas)
        fabricas = BaseDatosMemoriaFabrica.tables!!.getAllFabricas()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            fabricas
        )

        listaDeFabricas.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val btnAgregarFabrica = findViewById<Button>(R.id.btn_agregar_fabricas)
        btnAgregarFabrica.setOnClickListener {
            goToActivity(DatosFabrica::class.java)
        }

        registerForContextMenu(listaDeFabricas)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fabricas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                goToActivity(DatosFabrica::class.java, fabricas[posicionItemSeleccionado])
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogo(fabricas[posicionItemSeleccionado].id)
                return true
            }

            R.id.mi_ver_laptops -> {
                goToActivity(ListaDeLaptops::class.java, fabricas[posicionItemSeleccionado])
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        datosDeFabrica: Fabrica? = null
    ) {
        val intent = Intent(this, activityClass)
        if (datosDeFabrica != null) {
            intent.apply {
                putExtra("fabricaSeleccionada", datosDeFabrica)
            }
        }
        startActivity(intent)
    }

    private fun abrirDialogo(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton("Si") { _, _ ->
            BaseDatosMemoriaFabrica.tables!!.borrarFabrica(registerIndex)
            fabricas.clear()
            fabricas.addAll(BaseDatosMemoriaFabrica.tables!!.getAllFabricas())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("No", null)

        builder.create().show()
    }
}