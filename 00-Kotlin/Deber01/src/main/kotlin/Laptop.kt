package org.example

import java.io.*
import java.text.SimpleDateFormat
import java.util.*

data class Laptop(
    var id: Int,
    var modelo: String,
    var anio: Int,
    var precio: Double,
    var fechaFabricacion: Date,
    var disponible: Boolean,
    var fabricaId: Int
) : Serializable {
    companion object {
        private const val FILENAME = "laptop.dat"

        fun agregar(
            scanner: Scanner,
            sdf: SimpleDateFormat,
            laptopList: MutableList<Laptop>,
            fabricaList: MutableList<Fabrica>
        ) {
            if (fabricaList.isEmpty()) {
                println("---------------------------------------------")
                println("No hay fábricas disponibles. Por favor, agregue una fábrica primero.")
                return
            }
            scanner.nextLine()
            println("---------------------------------------------")
            println("Agregar una nueva Laptop:")
            print("Modelo: ")
            val modelo = scanner.nextLine()
            print("Año: ")
            val anio = scanner.nextInt()
            print("Precio: ")
            val precio = scanner.nextDouble()
            scanner.nextLine()
            print("Fecha de Fabricación (dd-MM-yyyy): ")
            val fechaFabricacion = sdf.parse(scanner.nextLine())
            print("Disponible (true/false): ")
            val disponible = scanner.nextBoolean()
            println("---------------------------------------------")
            println("Seleccione la Fábrica ID de la siguiente lista:")
            Fabrica.ver(fabricaList)
            println("---------------------------------------------")
            print("Ingrese el ID de la Fábrica: ")
            val fabricaId = scanner.nextInt()
            val fabricaExistente = fabricaList.find { it.id == fabricaId }

            if (fabricaExistente == null) {
                println("---------------------------------------------")
                println("Fábrica no encontrada. Laptop no creada.")
                return
            }

            val laptopId = if (laptopList.isEmpty()) 1 else laptopList.maxOf { it.id } + 1
            val laptop = Laptop(laptopId, modelo, anio, precio, fechaFabricacion, disponible, fabricaId)
            laptopList.add(laptop)
            println("---------------------------------------------")
            println("Laptop creada y agregada a la fábrica.")
        }

        fun ver(laptopList: List<Laptop>) {
            println("---------------------------------------------")
            println("Lista de Laptops:")
            laptopList.forEach { laptop ->
                println("---------------------------------------------")
                println("Laptop ${laptop.id}:")
                println("ID = ${laptop.id}")
                println("Modelo = ${laptop.modelo}")
                println("Año = ${laptop.anio}")
                println("Precio = ${laptop.precio}")
                println("Fecha de Fabricación = ${laptop.fechaFabricacion}")
                println("Disponible = ${laptop.disponible}")
                println("Fábrica ID = ${laptop.fabricaId}")
            }
        }

        fun actualizar(
            scanner: Scanner,
            sdf: SimpleDateFormat,
            laptopList: MutableList<Laptop>,
            fabricaList: MutableList<Fabrica>
        ) {
            println("---------------------------------------------")
            println("Actualizar información de una Laptop:")
            ver(laptopList)
            println("---------------------------------------------")
            print("Ingrese el ID de la Laptop: ")

            val id = scanner.nextInt()
            val laptop = laptopList.find { it.id == id }

            if (laptop != null) {
                scanner.nextLine()
                var continuar = true

                while (continuar) {
                    println("---------------------------------------------")
                    println("""
                |Seleccione el campo que desea actualizar:
                |1. Modelo
                |2. Año
                |3. Precio
                |4. Fecha de Fabricación
                |5. Disponible
                |6. Fábrica ID
                |7. Salir
            """.trimMargin())

                    print("Opción: ")
                    when (scanner.nextInt()) {
                        1 -> {
                            scanner.nextLine()
                            println("---------------------------------------------")
                            print("Nuevo Modelo: ")
                            laptop.modelo = scanner.nextLine()
                            break
                        }

                        2 -> {
                            println("---------------------------------------------")
                            print("Nuevo Año: ")
                            laptop.anio = scanner.nextInt()
                            scanner.nextLine()
                            break
                        }

                        3 -> {
                            println("---------------------------------------------")
                            print("Nuevo Precio: ")
                            laptop.precio = scanner.nextDouble()
                            scanner.nextLine()
                            break
                        }

                        4 -> {
                            println("---------------------------------------------")
                            scanner.nextLine()
                            print("Nueva Fecha de Fabricación (dd-MM-yyyy): ")
                            laptop.fechaFabricacion = sdf.parse(scanner.nextLine())
                            break
                        }

                        5 -> {
                            println("---------------------------------------------")
                            print("Disponible (true/false): ")
                            laptop.disponible = scanner.nextBoolean()
                            scanner.nextLine()
                            break
                        }

                        6 -> {
                            println("---------------------------------------------")
                            println("Seleccione la nueva Fábrica ID de la siguiente lista:")
                            Fabrica.ver(fabricaList)
                            println("---------------------------------------------")
                            print("Ingrese el ID de la Fábrica: ")
                            val fabricaId = scanner.nextInt()
                            val fabricaExistente = fabricaList.find { it.id == fabricaId }

                            if (fabricaExistente == null) {
                                println("---------------------------------------------")
                                println("Fábrica no encontrada. No se pudo actualizar la Laptop.")
                            } else {
                                laptop.fabricaId = fabricaId
                            }
                            scanner.nextLine()
                            break
                        }

                        7 -> continuar = false
                        else -> {
                            println("---------------------------------------------")
                            println ("Opción no válida. Por favor, intente de nuevo.")
                        }
                    }


                    println("---------------------------------------------")
                    if (continuar) println("Campo actualizado.")
                }

                println("Información de la Laptop actualizada.")
            } else {
                println("---------------------------------------------")
                println("Laptop no encontrada.")
            }
        }

        fun borrar(scanner: Scanner, laptopList: MutableList<Laptop>) {
            println("---------------------------------------------")
            println("Borrar una Laptop:")
            ver(laptopList)
            println("---------------------------------------------")
            print("Ingrese el ID de la Laptop: ")
            val id = scanner.nextInt()
            if (laptopList.removeIf { it.id == id }) {
                println("---------------------------------------------")
                println("Laptop borrada.")
            } else {
                println("---------------------------------------------")
                println("Laptop no encontrada.")
            }
        }

        fun guardar(laptopList: List<Laptop>) {
            try {
                ObjectOutputStream(FileOutputStream(FILENAME)).use { oos ->
                    oos.writeObject(laptopList)
                }
                println("---------------------------------------------")
                println("Laptops guardadas en archivo.")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun leer(): MutableList<Laptop>? {
            return try {
                ObjectInputStream(FileInputStream(FILENAME)).use { ois ->
                    ois.readObject() as MutableList<Laptop>
                }
            } catch (e: IOException) {
                null
            } catch (e: ClassNotFoundException) {
                null
            }
        }
    }
}
