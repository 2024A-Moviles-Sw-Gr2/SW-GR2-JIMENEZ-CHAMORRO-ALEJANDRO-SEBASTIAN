package org.example

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val fabricaList = Fabrica.leer() ?: mutableListOf()
    val laptopList = Laptop.leer() ?: mutableListOf()

    while (true) {
        println("\nOPCIONES:")
        println("---------------------------------------------")
        println("LAPTOP")
        println("1. Agregar Laptop")
        println("2. Ver laptops")
        println("3. Actualizar información de laptop")
        println("4. Borrar laptop")
        println("---------------------------------------------")
        println("FÁBRICA")
        println("5. Agregar fábrica de laptops")
        println("6. Ver fábricas")
        println("7. Actualizar información de fábrica")
        println("8. Borrar fábrica")
        println("0. Salir")
        println("---------------------------------------------")
        print("Seleccione una opción: ")

        when (scanner.nextInt()) {
            1 -> Laptop.agregar(scanner, sdf, laptopList, fabricaList)
            2 -> Laptop.ver(laptopList)
            3 -> Laptop.actualizar(scanner, sdf, laptopList, fabricaList)
            4 -> Laptop.borrar(scanner, laptopList)
            5 -> Fabrica.agregar(scanner, fabricaList)
            6 -> Fabrica.ver(fabricaList)
            7 -> Fabrica.actualizar(scanner, fabricaList)
            8 -> Fabrica.borrar(scanner, fabricaList, laptopList)
            0 -> {
                Fabrica.guardar(fabricaList)
                Laptop.guardar(laptopList)
                println("Saliendo...")
                return
            }
            else -> println("Opción no válida")
        }
    }
}
