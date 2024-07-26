package com.example.deber02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context?
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val tablaFabrica = """
            CREATE TABLE Fabrica(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100),
                lugar VARCHAR(50),
                anio_fundacion INTEGER
            );
        """.trimIndent()

        val tablaLaptop = """
            CREATE TABLE Laptop(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modelo VARCHAR(100),
                anio INTEGER,
                precio FLOAT,
                fabrica_id INTEGER,
                FOREIGN KEY (fabrica_id) REFERENCES Fabrica(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(tablaFabrica)
        db?.execSQL(tablaLaptop)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearFabrica(
        nombre: String,
        lugar: String,
        anio_fundacion: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombre", nombre)
        valuesToStore.put("lugar", lugar)
        valuesToStore.put("anio_fundacion", anio_fundacion)

        val storeResult = writeDB.insert(
            "Fabrica",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun getAllFabricas(): ArrayList<Fabrica> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM Fabrica
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<Fabrica>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    Fabrica(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun actualizarFabrica(
        id: Int,
        nombre: String,
        lugar: String,
        anio_fundacion: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombre", nombre)
        valuesToUpdate.put("lugar", lugar)
        valuesToUpdate.put("anio_fundacion", anio_fundacion)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "Fabrica",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun borrarFabrica(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "Fabrica",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun crearLaptop(
        modelo: String,
        anio: Int,
        precio: Float,
        fabrica_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("modelo", modelo)
        valuesToStore.put("anio", anio)
        valuesToStore.put("precio", precio)
        valuesToStore.put("fabrica_id", fabrica_id)

        val storeResult = writeDB.insert(
            "Laptop",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun getLaptopsDeFabrica(fabrica_id: Int): ArrayList<Laptop> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM Laptop WHERE fabrica_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(fabrica_id.toString())
        )
        val response = arrayListOf<Laptop>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    Laptop(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getFloat(3),
                        queryResult.getInt(4)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun actualizarLaptop(
        id: Int,
        modelo: String,
        anio: Int,
        precio: Float,
        fabrica_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("modelo", modelo)
        valuesToUpdate.put("anio", anio)
        valuesToUpdate.put("precio", precio)
        valuesToUpdate.put("fabrica_id", fabrica_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "Laptop",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun borrarLaptop(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "Laptop",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}
