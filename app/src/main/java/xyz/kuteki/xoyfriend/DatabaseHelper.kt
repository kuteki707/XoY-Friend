package xyz.kuteki.xoyfriend

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "FunctionDatabase"
        private const val TABLE_FUNCTIONS = "Functions"
        private const val KEY_ID = "id"
        private const val KEY_FUNCTION = "function"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FUNCTIONS_TABLE = ("CREATE TABLE " + TABLE_FUNCTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FUNCTION + " TEXT" + ")")
        db.execSQL(CREATE_FUNCTIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FUNCTIONS")
        onCreate(db)
    }

    fun addFunction(function: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_FUNCTION, function)
        db.insert(TABLE_FUNCTIONS, null, values)
        db.close()
    }

    fun getAllFunctions(): List<String> {
        val functions = ArrayList<String>()
        val selectQuery = "SELECT  * FROM $TABLE_FUNCTIONS ORDER BY $KEY_ID DESC"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                functions.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return functions
    }
}