package com.freight.shipper.core.persistence.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freight.shipper.FreightApplication
import com.freight.shipper.core.persistence.db.dao.*
import com.freight.shipper.model.*

@Database(
    version = 1,
    entities = [Country::class, State::class, LoadCategory::class,
        LoadStatus::class, VehicleType::class, MasterConfig::class]
)
abstract class RoomDb : RoomDatabase() {

    // region - DAO accessors
    abstract fun imageDao(): ImageDao

    abstract fun configDao(): ConfigDao
    abstract fun countryDao(): CountryDao
    abstract fun loadStatusDao(): LoadStatusDao
    abstract fun loadCategoryDao(): LoadCategoryDao
    abstract fun vehicleDao(): VehicleDao
    // endregion

    companion object {
        val instance: RoomDb by lazy {
            Room.databaseBuilder(
                FreightApplication.instance.applicationContext,
                RoomDb::class.java, "Crop.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}