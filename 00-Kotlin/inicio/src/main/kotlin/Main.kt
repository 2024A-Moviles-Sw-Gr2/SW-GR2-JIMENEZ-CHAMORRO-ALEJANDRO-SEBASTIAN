import java.util.*

fun main() {
    println("Hola Mundo!")

    // Inmutables (No se pueden RE ASIGNAR "="
    val inmutable: String = "Adrián"

    // Mutables
    var mutable: String = "Vicente"
    mutable = "Adrian"

    // VAL >>>>>>>> VAR

    // Duck Typing
    // El lenguaje ya interpreta el tipo de dato de la variable, por ello en su mayoría no es necesario poner
    // el tipo de variable.
    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()      // Borrar los espacios en blancos

    // Variables primitivas
    val nombre: String = "Adrian"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    // Clases en Java
    val fechaNacimiento: Date = Date()

    // when (switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            print("Casado")
        }

        ("S") -> {
            print("Soltero")
        }

        else -> {
            print("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //if else chikito

    calcularSueldo(10.0)
    calcularSueldo(10.0, bonoEspecial = 20.0)
}

// void ==> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double,                 // Requerido
    tasa: Double = 12.0,            // Opcional
    bonoEspecial: Double? = null    // El '?' significa que la variable puede ser nula
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class Números(protected val numeroUno: Int, protected val numeroDos: Int) {
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializando...")
    }
}

class Suma(unoParámetro: Int, dosParámetro: Int) : Números(unoParámetro, dosParámetro) {
    public val soyPublicoExplicito: String = "Explicito" // Públicas
    val soyPublicoImplícito: String = "Implícito" // Públicas (propiedades, métodos)

    init { // Bloque Código Constructor primario
        // this.unoParámetro // ERROR no existe
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, métodos)
        numeroDos // this. OPCIONAL (propiedades, métodos)
        this.soyPublicoExplicito
        soyPublicoImplícito // this. OPCIONAL (propiedades, métodos)
    }

    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }

    companion object { // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }
    }
}