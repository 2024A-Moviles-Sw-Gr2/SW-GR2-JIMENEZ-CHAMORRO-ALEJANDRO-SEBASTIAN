package org.example

import java.io.*
import java.util.*

data class Fabrica(
    var id: Int,
    var nombre: String,
    var ubicacion: String,
    var anioFundacion: Int,
    var operativa: Boolean
) : Serializable {
    companion object {
        private const val FILENAME = "fabrica.dat"

        fun agregar(scanner: Scanner, fabricaList: MutableList<Fabrica>) {
            scanner.nextLine()
            println("---------------------------------------------")
            println("Agregar una nueva Fábrica:")
            print("Nombre: ")
            val nombre = scanner.nextLine()
            print("Ubicación: ")
            val ubicacion = scanner.nextLine()
            print("Año de Fundación: ")
            val anioFundacion = scanner.nextInt()
            print("Operativa (true/false): ")
            val operativa = scanner.nextBoolean()

            val fabricaId = if (fabricaList.isEmpty()) 1 else fabricaList.maxOf { it.id } + 1
            val fabrica = Fabrica(fabricaId, nombre, ubicacion, anioFundacion, operativa)
            fabricaList.add(fabrica)
            println("---------------------------------------------")
            println("Fábrica creada.")
        }

        fun ver(fabricaList: List<Fabrica>) {
            println("---------------------------------------------")
            println("Lista de Fábricas:")
            fabricaList.forEach { fabrica ->
                println("---------------------------------------------")
                println("Fábrica ${fabrica.id}:")
                println("ID = ${fabrica.id}")
                println("Nombre = ${fabrica.nombre}")
                println("Ubicación = ${fabrica.ubicacion}")
                println("Año de Fundación = ${fabrica.anioFundacion}")
                println("Operativa = ${fabrica.operativa}")
            }
        }

        fun actualizar(scanner: Scanner, fabricaList: MutableList<Fabrica>) {
            println("---------------------------------------------")
            println("Actualizar información de una Fábrica:")
            ver(fabricaList)
            println("---------------------------------------------")
            print("Ingrese el ID de la Fábrica: ")
            val id = scanner.nextInt()
            val fabrica = fabricaList.find { it.id == id }

            if (fabrica != null) {
                scanner.nextLine()
                var continuar = true

                while (continuar) {
                    println("---------------------------------------------")
                    println("""
                |Seleccione el campo que desea actualizar:
                |1. Nombre
                |2. Ubicación
                |3. Año de Fundación
                |4. Operativa
                |5. Salir
            """.trimMargin())

                    print("Opción: ")
                    when (scanner.nextInt()) {
                        1 -> {
                            println("---------------------------------------------")
                            scanner.nextLine()
                            print("Nuevo Nombre: ")
                            fabrica.nombre = scanner.nextLine()
                            break
                        }
                        2 -> {
                            println("---------------------------------------------")
                            scanner.nextLine()
                            print("Nueva Ubicación: ")
                            fabrica.ubicacion = scanner.nextLine()
                            break
                        }
                        3 -> {
                            println("---------------------------------------------")
                            scanner.nextLine()
                            print("Nuevo Año de Fundación: ")
                            fabrica.anioFundacion = scanner.nextInt()
                            break
                        }
                        4 -> {
                            println("---------------------------------------------")
                            scanner.nextLine()
                            print("Operativa (true/false): ")
                            fabrica.operativa = scanner.nextBoolean()
                            scanner.nextLine()
                            break
                        }
                        5 -> continuar = false
                        else -> println("Opción no válida. Por favor, intente de nuevo.")
                    }

                    println("---------------------------------------------")
                    if (continuar) println("Campo actualizado.")
                }

                println("Información de la Fábrica actualizada.")
            } else {
                println("---------------------------------------------")
                println("Fábrica no encontrada.")
            }
        }

        fun borrar(scanner: Scanner, fabricaList: MutableList<Fabrica>, laptopList: MutableList<Laptop>) {
            println("---------------------------------------------")
            println("Borrar una Fábrica:")
            ver(fabricaList)
            println("---------------------------------------------")
            print("Ingrese el ID de la Fábrica: ")
            val id = scanner.nextInt()
            if (fabricaList.removeIf { it.id == id }) {
                laptopList.removeIf { it.fabricaId == id }
                println("---------------------------------------------")
                println("Fábrica y sus laptops asociadas borradas.")
            } else {
                println("---------------------------------------------")
                println("Fábrica no encontrada.")
            }
        }

        fun guardar(fabricaList: List<Fabrica>) {
            try {
                ObjectOutputStream(FileOutputStream(FILENAME)).use { oos ->
                    oos.writeObject(fabricaList)
                }
                println("---------------------------------------------")
                println("Fábricas guardadas en archivo.")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun leer(): MutableList<Fabrica>? {
            return try {
                ObjectInputStream(FileInputStream(FILENAME)).use { ois ->
                    ois.readObject() as MutableList<Fabrica>
                }
            } catch (e: IOException) {
                null
            } catch (e: ClassNotFoundException) {
                null
            }
        }
    }
}
