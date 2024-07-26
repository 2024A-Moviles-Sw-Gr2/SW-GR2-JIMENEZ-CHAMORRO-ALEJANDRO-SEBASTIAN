package com.example.deber02

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListaDeLaptops : AppCompatActivity() {
    private var allLaptops: ArrayList<Laptop> = arrayListOf()
    private var adapter: ArrayAdapter<Laptop>? = null
    private var fabricaSeleccionada: Fabrica? = null
    private var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_de_laptops)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_lista_de_laptops)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fabricaSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("fabricaSeleccionada", Fabrica::class.java)
        } else {
            intent.getParcelableExtra<Fabrica>("fabricaSeleccionada")
        }

        val laptopsFabrica = findViewById<TextView>(R.id.txt_nombre_fabrica)
        laptopsFabrica.text = fabricaSeleccionada!!.nombre

        BaseDatosMemoriaFabrica.tables = SqliteHelper(this)
        val listaDeLaptops = findViewById<ListView>(R.id.lv_laptops)
        allLaptops = BaseDatosMemoriaFabrica.tables!!.getLaptopsDeFabrica(fabricaSeleccionada!!.id)
        adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, allLaptops
        )

        listaDeLaptops.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val btnAgregarLaptop = findViewById<Button>(R.id.btn_agregar_laptop)
        btnAgregarLaptop.setOnClickListener {
            goToActivity(DatosLaptop::class.java, null, fabricaSeleccionada!!)
        }

        registerForContextMenu(listaDeLaptops)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_laptops, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_laptop -> {
                goToActivity(
                    DatosLaptop::class.java,
                    allLaptops[posicionItemSeleccionado],
                    fabricaSeleccionada!!,
                    false
                )
                return true
            }

            R.id.mi_eliminar_laptop -> {
                abrirDialogo(allLaptops[posicionItemSeleccionado].id)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        datosDeLaptop: Laptop? = null,
        datosDeFabrica: Fabrica? = null,
        crear: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if (datosDeLaptop != null) {
            intent.apply {
                putExtra("laptopSeleccionada", datosDeLaptop)
            }
        }

        if (datosDeFabrica != null) {
            intent.apply {
                putExtra("fabricaSeleccionada", datosDeFabrica)
            }
        }

        intent.apply {
            putExtra("crear", crear)
        }
        startActivity(intent)
    }

    private fun abrirDialogo(registerIndex: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton("Si") { _, _ ->
            BaseDatosMemoriaFabrica.tables!!.borrarLaptop(registerIndex)
            allLaptops.clear()
            allLaptops.addAll(
                BaseDatosMemoriaFabrica.tables!!.getLaptopsDeFabrica(
                    fabricaSeleccionada!!.id
                )
            )
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("No", null)

        builder.create().show()
    }
}