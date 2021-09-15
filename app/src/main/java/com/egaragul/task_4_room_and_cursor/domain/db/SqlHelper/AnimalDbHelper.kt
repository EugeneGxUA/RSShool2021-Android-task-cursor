package com.egaragul.task_4_room_and_cursor.domain.db.SqlHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class AnimalDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AnimalReader.db"

        private const val SQL_CREATE_ENTRIES = """CREATE TABLE IF NOT EXISTS ${AnimalReaderContract.AnimalEntry.TABLE_NAME}
            (${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL,  ${AnimalReaderContract.AnimalEntry.COLUMN_NAME} TEXT NOT NULL,
            ${AnimalReaderContract.AnimalEntry.COLUMN_AGE} INTEGER NOT NULL, ${AnimalReaderContract.AnimalEntry.COLUMN_BREED} TEXT NOT NULL)"""

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${AnimalReaderContract.AnimalEntry.TABLE_NAME}"
    }
}

