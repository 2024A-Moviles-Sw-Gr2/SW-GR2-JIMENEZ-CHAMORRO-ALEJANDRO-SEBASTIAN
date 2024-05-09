import java.util.*

fun main(){
    println("Hola Mundo!")

    // Inmutables (No se pueden RE ASIGNAR "="
    val inmutable: String = "Adrián"

    // Mutables
    var mutable: String = "Vicente"
    mutable = "Adrian"

    // VAL >>>>>>>> VAR

    // Duck Typing
    // El lenguaje ya interpreta el tipo de dato de la variable, por ello en su mayoria no es necesario poner
    // el tipo de variable.
    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()      // Borrar los espacios en blancos

    // Variables primitivas
    val nombre:String = "Adrian"
    val sueldo:Double = 1.2
    val estadoCivil:Char = 'C'
    val mayorEdad:Boolean = true

    // Clases en Java
    val fechaNacimiento: Date = Date()
}
