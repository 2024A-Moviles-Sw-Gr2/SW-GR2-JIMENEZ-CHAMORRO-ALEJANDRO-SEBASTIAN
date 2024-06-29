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

    // Usando las clases
    val suma = Suma(1, 2)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    // Sumando
    suma.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    // Companion Object
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)
    // Arreglos estáticos
    val arregloEstático: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstático)
    // Arreglo dinámico
    val arregloDinámico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    println(arregloDinámico)
    arregloDinámico.add(7)
    arregloDinámico.add(8)
    println(arregloDinámico)

    // FOR EACH => Unit (devuelve void)
    // Iterar un arreglo
    println("\n ** Primera forma **")
    val respuestaForEach: Unit = arregloDinámico.forEach {
        valorActual: Int ->
            println("Valor actual: ${valorActual}");
        }

    // "it" (en ignlés "eso") significa el elemento iterado
    println("\n ** Segunda forma **")
    arregloDinámico.forEach { println("Valor actual (it): ${it}") }

    // MAP -> MUTA(Modifica, cambia) el arreglo
    // 1) Enviamos el nuevo valor de la iteración
    // 2) Nos devuelve un NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinámico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println("\n" + respuestaMap)

    val respuestaMapDos = arregloDinámico.map { it + 15 }

    println("\n" + respuestaMapDos)

    // Filter -> Filtrar el ARREGLO
    // 1) Devolver una expresión (TRUE O FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinámico
        .filter { valorActual: Int ->
            // Expresión o CONDICIÓN
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinámico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR & AND
    // OR -> ANY  (Alguna cumple?)
    // AND -> ALL (Todos cumplen?)
    val respuestaAny: Boolean = arregloDinámico
        .any{ valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) // True

    val respuestaAll: Boolean = arregloDinámico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) // False

    // REDUCE
    // 1) Acumulador
    // 2) Valor actual
    // 3) Nuevo acumulador
    val respuestaReduce: Int = arregloDinámico
        .reduce { acumulador, valorActual ->
            return@reduce acumulador + valorActual
        }
    println(respuestaReduce) // 36
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

    constructor(uno: Int?, dos: Int) : this(
        if (uno == null) 0 else uno,
        dos
    )

    constructor(uno: Int, dos: Int?) : this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor(uno: Int?, dos: Int?) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

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