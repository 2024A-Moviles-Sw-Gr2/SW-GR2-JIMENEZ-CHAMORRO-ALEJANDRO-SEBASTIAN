package com.example.a2024aswgr2asjc

class BBaseDatosMemoria {
    val arregloBEntrenador = arrayListOf<BEntrenador>()

    init {
        arregloBEntrenador
            .add(BEntrenador(1, "Entrenador 1", "Descripcion 1"))
        arregloBEntrenador
            .add(BEntrenador(2, "Entrenador 2", "Descripcion 2"))
        arregloBEntrenador
            .add(BEntrenador(3, "Entrenador 3", "Descripcion 3"))
    }
}