package com.example.deber02

class BaseDeDatosMemoriaFabrica {
    val arregloEntrenador = arrayListOf<Fabrica>()

    init {
        arregloEntrenador.add(Fabrica("Lenovo", "Quito", 2002, true))
        arregloEntrenador.add(Fabrica("HP", "Guayaquil", 2003, false))
        arregloEntrenador.add(Fabrica("Dell", "Cuenca", 2004, true))
    }
}