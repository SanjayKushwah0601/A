package com.freight.shipper.core.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freight.shipper.FreightApplication
import com.freight.shipper.core.db.dao.ImageDao
import com.freight.shipper.model.Image

@Database(entities = [Image::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    // region - DAO accessors
    abstract fun imageDao(): ImageDao
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