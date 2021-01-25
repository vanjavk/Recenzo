package me.vanjavk.recenzo.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import me.vanjavk.recenzo.model.Product


private const val DB_NAME = "items.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "items"
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Product::_id.name} integer primary key autoincrement, " +
        "${Product::title.name} text not null, " +
        "${Product::description.name} text not null, " +
        "${Product::picturePath.name} text not null " +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class RecenzoSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    RecenzoRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE) // prilika za upgrade!!!
        // kreiraj bekap bazu
        // bekapiraj podatke
        // upgrade stare baze
        // prekopiraj stare podatke u novu bazu
        onCreate(db)
    }

    // where nrResidents > ? , 100
    override fun delete(selection: String?, selectionArgs: Array<String>?)
            = writableDatabase.delete(TABLE_NAME, selection, selectionArgs)

    override fun insert(values: ContentValues?) = writableDatabase.insert(TABLE_NAME, null, values)

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = readableDatabase
        .query(TABLE_NAME,
            projection, // _id, title, explanation
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)

}