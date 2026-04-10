package com.example.mystarwarswiki.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mystarwarswiki.data.local.entity.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM people ORDER BY id ASC")
    fun getPeople(): Flow<List<PersonEntity>>

    @Query("SELECT * FROM people WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PersonEntity>)

    @Query("DELETE FROM people")
    suspend fun clearAll()
}