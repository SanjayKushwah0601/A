package com.crop.core.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crop.CropApplication
import com.crop.core.db.dao.ImageDao
import com.crop.model.Image

@Database(entities = [Image::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    // region - DAO accessors
    abstract fun imageDao(): ImageDao
    // endregion

    companion object {
        val instance: RoomDb by lazy {
            Room.databaseBuilder(
                CropApplication.instance.applicationContext,
                RoomDb::class.java, "Crop.db"
            ).build()
        }
    }
}