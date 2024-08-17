package com.example.deber003

import android.os.Parcelable

data class Cuenta(
    val propietario: String,
    val numeroCuenta: String,
    val tipoCuenta: String,
    val saldo: Double
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(propietario)
        parcel.writeString(numeroCuenta)
        parcel.writeString(tipoCuenta)
        parcel.writeDouble(saldo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cuenta> {
        override fun createFromParcel(parcel: android.os.Parcel): Cuenta {
            return Cuenta(parcel)
        }

        override fun newArray(size: Int): Array<Cuenta?> {
            return arrayOfNulls(size)
        }
    }
}