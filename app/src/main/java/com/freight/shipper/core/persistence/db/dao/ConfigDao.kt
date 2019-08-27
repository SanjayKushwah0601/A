package com.freight.shipper.core.persistence.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.model.MasterConfig

@Dao
interface ConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addConfig(values: MasterConfig)

    @Query("SELECT * FROM config LIMIT 1")
    fun getConfig(): MasterConfig
}