package com.freight.shipper.core.persistence.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freight.shipper.FreightApplication
import com.freight.shipper.core.persistence.db.dao.CountryDao
import com.freight.shipper.core.persistence.db.dao.ImageDao
import com.freight.shipper.core.persistence.db.dao.LoadCategoryDao
import com.freight.shipper.core.persistence.db.dao.LoadStatusDao
import com.freight.shipper.model.*

@Database(
    version = 1,
    entities = [Image::class, Country::class, State::class, LoadCategory::class,
        LoadStatus::class]
)
abstract class RoomDb : RoomDatabase() {

    // region - DAO accessors
    abstract fun imageDao(): ImageDao

    abstract fun countryDao(): CountryDao
    abstract fun loadStatusDao(): LoadStatusDao
    abstract fun loadCategoryDao(): LoadCategoryDao
    // endregion

    companion object {
        val instance: RoomDb by lazy {
            Room.databaseBuilder(
                FreightApplication.instance.applicationContext,
                RoomDb::class.java, "Crop.db"
            ).build()
        }
    }
}