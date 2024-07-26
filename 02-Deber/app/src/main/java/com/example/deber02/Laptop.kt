package com.example.deber02

import android.os.Parcel
import android.os.Parcelable

class Laptop(
    var id: Int,
    var modelo: String,
    var anio: Int,
    var precio: Float,
    var fabrica_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "$modelo ($anio) - $$precio"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(modelo)
        parcel.writeInt(anio)
        parcel.writeFloat(precio)
        parcel.writeInt(fabrica_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Laptop> {
        override fun createFromParcel(parcel: Parcel): Laptop {
            return Laptop(parcel)
        }

        override fun newArray(size: Int): Array<Laptop?> {
            return arrayOfNulls(size)
        }
    }
}