package com.alvarez.sergio.actraining.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NeoDAO {
    @Query("SELECT * FROM NeoEntity")
    fun getAll(): Flow<List<NeoEntity>>

    @Query("SELECT * FROM NeoEntity WHERE id = :id")
    fun findById(id: Int): Flow<NeoEntity>

    @Query("SELECT COUNT(id) FROM NeoEntity ")
    suspend fun newsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNeos(neo: List<NeoEntity>)
}
