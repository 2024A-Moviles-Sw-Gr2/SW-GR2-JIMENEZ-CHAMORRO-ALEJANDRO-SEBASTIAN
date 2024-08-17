package com.example.deber003

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CuentasAdapter(private val cuentas: List<Cuenta>) :
    RecyclerView.Adapter<CuentasAdapter.CuentasViewHolder>() {

    class CuentasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tv_nombre)
        val tvNumeroCuenta: TextView = view.findViewById(R.id.tv_numero_cuenta)
        val tvSaldo: TextView = view.findViewById(R.id.tv_saldo)
        val tvCuentaTipo: TextView = view.findViewById(R.id.tv_cuenta_tipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_account, parent, false)
        return CuentasViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuentasViewHolder, position: Int) {
        val cuenta = cuentas[position]
        holder.tvNombre.text = cuenta.propietario
        holder.tvNumeroCuenta.text = cuenta.numeroCuenta
        holder.tvSaldo.text = cuenta.saldo.toString()
        holder.tvCuentaTipo.text = cuenta.tipoCuenta
    }

    override fun getItemCount() = cuentas.size
}
