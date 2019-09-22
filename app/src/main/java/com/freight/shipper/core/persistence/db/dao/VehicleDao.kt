package com.freight.shipper.core.persistence.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.core.persistence.network.response.VehicleType

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicleTypeList(values: List<VehicleType>)

    @Query("SELECT * FROM vehicle_type")
    fun getVehicleTypeList(): List<VehicleType>
}