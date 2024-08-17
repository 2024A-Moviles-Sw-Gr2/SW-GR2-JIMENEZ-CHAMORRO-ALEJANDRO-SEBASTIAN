package com.example.a2024aswgr2asjc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?
) : SQLiteOpenHelper(contexto, "moviles", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
            CREATE TABLE ENTRENADOR
            (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
            )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun crearEntrenador(nombre: String, descripcion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = baseDatosEscritura
            .insert(
                "ENTRENADOR",
                null,
                valoresAGuardar
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(id: Int, nombre: String, descripcion: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR",
                valoresActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarEntrenadorPorID(id: Int): BEntrenador? {
        val baseDatosLectura = readableDatabase
        val scriptConsultarEntrenador = "SELECT * FROM ENTRENADOR WHERE ID = ?".trimIndent()
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = baseDatosLectura.rawQuery(
            scriptConsultarEntrenador,
            parametrosConsulta
        )
        val existeAlMenos = resultadoConsulta.moveToFirst()
        val arregloRespuesta = arrayListOf<BEntrenador>()
        if (existeAlMenos) {
            do {
                val entrenador = BEntrenador(
                    resultadoConsulta.getInt(0),
                    resultadoConsulta.getString(1),
                    resultadoConsulta.getString(2)
                )
                arregloRespuesta.add(entrenador)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()

        return if (arregloRespuesta.size > 0) arregloRespuesta[0] else null
    }
}
