package com.example.mystarwarswiki.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mystarwarswiki.data.local.dao.PersonDao
import com.example.mystarwarswiki.data.local.entity.PersonEntity

@Database(
    entities = [PersonEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}