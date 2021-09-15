package com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper

import android.content.ContentValues
import android.provider.BaseColumns
import com.egaragul.task_4_room_and_cursor.App
import com.egaragul.task_4_room_and_cursor.domain.data.Animal

class CursorHelper {

    private val dbHelper = AnimalDbHelper(App.getAppContext())
    private val dbWriter = dbHelper.writableDatabase
    private val dbReader = dbHelper.readableDatabase

    fun save(name: String, age: Int, breed: String) {
        val values = ContentValues().apply {
            put(AnimalReaderContract.AnimalEntry.COLUMN_NAME, name)
            put(AnimalReaderContract.AnimalEntry.COLUMN_AGE, age)
            put(AnimalReaderContract.AnimalEntry.COLUMN_BREED, breed)
        }

        val rawId = dbWriter.insert(AnimalReaderContract.AnimalEntry.TABLE_NAME, null, values)
    }

    fun read(): List<Animal> {
        val cursor = dbReader.rawQuery("""SELECT * FROM ${AnimalReaderContract.AnimalEntry.TABLE_NAME}""", null)

        val animals = mutableListOf<Animal>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val name = getString(getColumnIndex(com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper.AnimalReaderContract.AnimalEntry.COLUMN_NAME))
                val age = getInt(getColumnIndex(com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper.AnimalReaderContract.AnimalEntry.COLUMN_AGE))
                val breed = getString(getColumnIndex(com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper.AnimalReaderContract.AnimalEntry.COLUMN_BREED))

                animals.add(Animal(id, name, age, breed))
            }
        }
//
        cursor.close()
        return animals
    }

    fun update(animal: Animal): Boolean {
        val cv = ContentValues().apply {
            put(AnimalReaderContract.AnimalEntry.COLUMN_NAME, animal.name)
            put(AnimalReaderContract.AnimalEntry.COLUMN_AGE, animal.age)
            put(AnimalReaderContract.AnimalEntry.COLUMN_BREED, animal.breed)
        }
        val updatedRows = dbWriter.update(
            AnimalReaderContract.AnimalEntry.TABLE_NAME,
            cv,
            "_id = ${animal._id}",
            null
        )

        return updatedRows > 0
    }

    fun delete(animal: Animal): Boolean {
        val selection =
            """${AnimalReaderContract.AnimalEntry.COLUMN_NAME}='${animal.name}' AND ${AnimalReaderContract.AnimalEntry.COLUMN_AGE}='${animal.age}' AND ${AnimalReaderContract.AnimalEntry.COLUMN_BREED}='${animal.breed}'"""
        val deletedRows = dbWriter.delete(AnimalReaderContract.AnimalEntry.TABLE_NAME, selection, null)
        return deletedRows > 0
    }

    fun onDestroy() {
        dbHelper.close()
    }

    fun getItemById(id: Int): Animal? {
        val cursor = dbReader.rawQuery(
            """SELECT ${BaseColumns._ID}, ${AnimalReaderContract.AnimalEntry.COLUMN_NAME}, ${AnimalReaderContract.AnimalEntry.COLUMN_AGE}, ${AnimalReaderContract.AnimalEntry.COLUMN_BREED} FROM ${AnimalReaderContract.AnimalEntry.TABLE_NAME} WHERE _id='$id'""",
            null
        )
        if (cursor.count == 1) {
            cursor.use {
                if (it.moveToFirst()) {
                    val fromDbId = it.getInt(it.getColumnIndex(BaseColumns._ID))
                    val name = it.getString(it.getColumnIndex(AnimalReaderContract.AnimalEntry.COLUMN_NAME))
                    val age = it.getInt(it.getColumnIndex(AnimalReaderContract.AnimalEntry.COLUMN_AGE))
                    val breed = it.getString(it.getColumnIndex(AnimalReaderContract.AnimalEntry.COLUMN_BREED))

                    return Animal(fromDbId, name, age, breed)
                }
                return null
            }
        } else {
            cursor.close()
            return null
        }
    }
}