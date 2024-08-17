package com.example.deber02

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {
    private var fabricas: ArrayList<Fabrica> = arrayListOf()
    private var adapter: ArrayAdapter<Fabrica>? = null
    private var posicionItemSeleccionado = -1
    private lateinit var mapa: GoogleMap
    var permisos = false

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

        solicitarPermiso()
        iniciarLógicaMapa()
    }

    private fun solicitarPermiso() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if(tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(nombrePermisoFine, nombrePermisoCoarse),
                1)

        }
    }

    private fun iniciarLógicaMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            mapa = googleMap
            establecerConfiguraciónMapa()
            establecerPuntosDeFábricasEnElMapa()
        }
    }

    private fun establecerConfiguraciónMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = androidx.core.content.ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = androidx.core.content.ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = (permisoFine == android.content.pm.PackageManager.PERMISSION_GRANTED) &&
                    (permisoCoarse == android.content.pm.PackageManager.PERMISSION_GRANTED)
            if(tienePermisos) {
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun establecerPuntosDeFábricasEnElMapa() {
        val zoom = 10f
        var tituloAuxiliarPuntoEnElMapa = ""
        var auxiliarLatLng: LatLng? = null

        this.fabricas.forEach {
            auxiliarLatLng = LatLng(it.latitud.toDouble(), it.longitud.toDouble())
            tituloAuxiliarPuntoEnElMapa = it.nombre
            mapa.addMarker(
                MarkerOptions().position(auxiliarLatLng!!).title(tituloAuxiliarPuntoEnElMapa)
            )!!.tag = tituloAuxiliarPuntoEnElMapa
        }
        if (this.fabricas.size > 0) {
            mapa.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    auxiliarLatLng!!, zoom
                )
            )
        }
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