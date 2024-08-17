package com.example.deber02

import android.os.Parcel
import android.os.Parcelable

class Fabrica(nombre: String, ubicacion: String, anio_fundacion: Int, es_operativa: Boolean) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("nombre"),
        TODO("ubicacion"),
        TODO("anio_fundacion"),
        TODO("es_operativa")
    ) {
    }

    override fun toString(): String {
        return "Fabrica(nombre='$nombre', ubicacion='$ubicacion', anio_fundacion=$anio_fundacion, es_operativa=$es_operativa)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fabrica> {
        override fun createFromParcel(parcel: Parcel): Fabrica {
            return Fabrica(parcel)
        }

        override fun newArray(size: Int): Array<Fabrica?> {
            return arrayOfNulls(size)
        }
    }
}
