package com.example.deber003

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // RecyclerView para botones de opciones
        val opcionesRecyclerView: RecyclerView = findViewById(R.id.rv_botones_opciones)
        opcionesRecyclerView.adapter =
            OpcionesAdapter(
                listOf(
                    "Todos",
                    "Cuentas",
                    "Tarjetas",
                    "Préstamos",
                    "Inversiones"
                )
            )
        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        opcionesRecyclerView.layoutManager = layoutManager

        // RecyclerView para tarjetas de cuentas
        val cuentasRecyclerView: RecyclerView = findViewById(R.id.rv_cuentas)
        val cuentasList = listOf(
            Cuenta(
                "Juan Perez",
                "123456789",
                "Ahorros",
                1500.0
            ),
            Cuenta(
                "Juan Perez",
                "987654321",
                "Corriente",
                3000.0
            ),
            Cuenta(
                "Juan Perez",
                "115156156",
                "Corriente",
                80000.0
            ),
        )
        cuentasRecyclerView.adapter = CuentasAdapter(cuentasList)
        cuentasRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // RecyclerView para opciones de acciones
        val accionesRecyclerView: RecyclerView = findViewById(R.id.rv_opciones)
        val accionesList = listOf(
            Accion("Transferir dinero", R.drawable.transferir_dinero),
            Accion("Recibir dinero", R.drawable.recibir_dinero),
            Accion("Pagar D1", R.drawable.de_una),
            Accion("Pagar servicios", R.drawable.pagar_servicio),
            Accion("Pagar tarjetas", R.drawable.pagar_tarjetas),
            Accion("Retirar sin tarjeta", R.drawable.retirar_sin_tarjeta),
            Accion("Recargar celular", R.drawable.recargar_celular),
            Accion("Regalo", R.drawable.transferir_regalo)
        )
        accionesRecyclerView.adapter = AccionesAdapter(accionesList)
        accionesRecyclerView.layoutManager = GridLayoutManager(this, 3)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }

                R.id.nav_search -> {
                    true
                }

                R.id.nav_notifications -> {
                    true
                }

                R.id.nav_profile -> {
                    true
                }

                else -> false
            }
        }
    }
}
