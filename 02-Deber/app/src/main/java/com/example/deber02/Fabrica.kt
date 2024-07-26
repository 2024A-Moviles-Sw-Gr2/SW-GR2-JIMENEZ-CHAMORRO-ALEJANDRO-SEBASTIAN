package com.example.deber02

import android.os.Parcel
import android.os.Parcelable

class Fabrica(
    var id: Int,
    var nombre: String,
    var lugar: String,
    var año_fundacion: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "$nombre ($año_fundacion) - $lugar"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(lugar)
        parcel.writeInt(año_fundacion)
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

