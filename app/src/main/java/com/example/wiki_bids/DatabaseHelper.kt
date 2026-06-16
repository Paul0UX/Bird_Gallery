package com.example.wiki_bids

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "WikiBids.db"
        private const val DATABASE_VERSION = 2 // Incremented version

        // Table Users
        private const val TABLE_USERS = "users"
        private const val COL_USER_ID = "id"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"

        // Table Birds
        private const val TABLE_BIRDS = "birds"
        private const val COL_BIRD_ID = "id"
        private const val COL_BIRD_NAME = "name"
        private const val COL_BIRD_LOCATION = "location"
        private const val COL_BIRD_COUNTRY = "country" // New column
        private const val COL_BIRD_SEX = "sex"
        private const val COL_BIRD_DESCRIPTION = "description"
        private const val COL_BIRD_PHOTO = "photo_path"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_USERNAME TEXT UNIQUE," +
                "$COL_PASSWORD TEXT)")
        
        val createBirdsTable = ("CREATE TABLE $TABLE_BIRDS (" +
                "$COL_BIRD_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_BIRD_NAME TEXT," +
                "$COL_BIRD_LOCATION TEXT," +
                "$COL_BIRD_COUNTRY TEXT," +
                "$COL_BIRD_SEX TEXT," +
                "$COL_BIRD_DESCRIPTION TEXT," +
                "$COL_BIRD_PHOTO TEXT)")

        db?.execSQL(createUsersTable)
        db?.execSQL(createBirdsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_BIRDS ADD COLUMN $COL_BIRD_COUNTRY TEXT DEFAULT ''")
        }
    }

    // --- User Operations ---
    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_USERNAME, username)
            put(COL_PASSWORD, password)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_USERNAME=? AND $COL_PASSWORD=?", arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // --- Bird Operations ---
    fun addBird(name: String, location: String, country: String, sex: String, description: String, photoPath: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_BIRD_NAME, name)
            put(COL_BIRD_LOCATION, location)
            put(COL_BIRD_COUNTRY, country)
            put(COL_BIRD_SEX, sex)
            put(COL_BIRD_DESCRIPTION, description)
            put(COL_BIRD_PHOTO, photoPath)
        }
        return db.insert(TABLE_BIRDS, null, values)
    }

    fun getAllBirds(): List<Bird> {
        val birdList = mutableListOf<Bird>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BIRDS", null)
        if (cursor.moveToFirst()) {
            do {
                val bird = Bird(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_BIRD_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_LOCATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_COUNTRY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_SEX)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BIRD_PHOTO))
                )
                birdList.add(bird)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return birdList
    }

    fun updateBird(id: Int, name: String, location: String, country: String, sex: String, description: String, photoPath: String?): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_BIRD_NAME, name)
            put(COL_BIRD_LOCATION, location)
            put(COL_BIRD_COUNTRY, country)
            put(COL_BIRD_SEX, sex)
            put(COL_BIRD_DESCRIPTION, description)
            put(COL_BIRD_PHOTO, photoPath)
        }
        return db.update(TABLE_BIRDS, values, "$COL_BIRD_ID=?", arrayOf(id.toString()))
    }

    fun deleteBird(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_BIRDS, "$COL_BIRD_ID=?", arrayOf(id.toString()))
    }
}

data class Bird(
    val id: Int,
    val name: String,
    val location: String,
    val country: String,
    val sex: String,
    val description: String,
    val photoPath: String?
)
