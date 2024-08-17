package com.example.deber003

import android.os.Parcelable

data class Accion   (
    val nombre: String,
    val icono: Int
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(icono)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Accion> {
        override fun createFromParcel(parcel: android.os.Parcel): Accion {
            return Accion(parcel)
        }

        override fun newArray(size: Int): Array<Accion?> {
            return arrayOfNulls(size)
        }
    }
}