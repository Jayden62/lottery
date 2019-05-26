package lottery.com.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import lottery.com.local.model.Item
import lottery.com.model.Service

class LocalHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_ITEM_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT" + ")")
        db.execSQL(CREATE_ITEM_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

//    fun addName(name: Name) {
//        val values = ContentValues()
//        values.put(COLUMN_NAME, name.userName)
//        val db = this.writableDatabase
//        db.insert(TABLE_NAME, null, values)
//        db.close()
//    }

    fun saveItems(items: MutableList<Service>?) {
        if (items == null) {
            return
        }
        var db: SQLiteDatabase? = null
        val values = ContentValues()
        for (it in items) {
            values.put(COLUMN_ID, it.id)
            values.put(COLUMN_NAME, it.name)
            db = this.writableDatabase
            db.insert(TABLE_NAME, null, values)
        }
        db?.close()
    }

    val getItems: MutableList<Service>?
        get() {
            val result: MutableList<Service> = mutableListOf()
            val db = this.writableDatabase
            val query = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(query, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex("service_id"))
                    val name = cursor.getString(cursor.getColumnIndex("service_name"))
                    val price = cursor.getInt(cursor.getColumnIndex("price"))
                    val time = cursor.getInt(cursor.getColumnIndex("time_todo"))
                    val detail = cursor.getString(cursor.getColumnIndex("detail_service"))
                    val typeId = cursor.getInt(cursor.getColumnIndex("type_service_id"))
                    val item = Service(id, name, price, time, detail, typeId)
                    result.add(item)
                }
            }
            return result
        }

//    fun getAllName(): Cursor? {
//        val db = this.readableDatabase
//        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
//    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "dental.db"
        const val TABLE_NAME = "Cart"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}