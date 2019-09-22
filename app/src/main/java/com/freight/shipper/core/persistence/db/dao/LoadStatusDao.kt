package com.freight.shipper.core.persistence.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.core.persistence.network.response.LoadStatus

@Dao
interface LoadStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(values: List<LoadStatus>)

    @Query("SELECT * FROM load_status")
    fun getLoadStatusList(): List<LoadStatus>
}