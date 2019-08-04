package com.freight.shipper.core.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.model.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(values: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(values: List<Image>)

    @Query("SELECT * FROM image WHERE image.id =:id LIMIT 1")
    fun fetchImageById(id: Long): Image?

    @Query("SELECT * FROM image WHERE image.id IN (:ids)")
    fun fetchImagesWithIds(ids: List<Long>): List<Image>

    @Query("SELECT * FROM image WHERE image.id IN (:ids)")
    fun fetchWorksWithIds(ids: List<Long>): LiveData<List<Image>>

    @Query("DELETE FROM image WHERE image.id= :id")
    fun removeById(id: Long)
}