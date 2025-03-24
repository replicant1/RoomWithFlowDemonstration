package com.example.roomwithflowdemonstration.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {
    @Insert
    suspend fun insert(regions:List<RegionEntity>)

    @Insert
    suspend fun insert(region: RegionEntity)

    @Query("delete from regions")
    fun deleteAll()

    @Query("select * from regions")
    fun getAllRegionsStream(): Flow<List<RegionEntity>>

    @Query("select * from regions")
    fun getAllRegions(): List<RegionEntity>
}