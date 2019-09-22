package com.freight.shipper.core.persistence.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.core.persistence.network.response.LoadCategory

@Dao
interface LoadCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(values: List<LoadCategory>)

    @Query("SELECT * FROM load_category")
    fun getLoadCategories(): List<LoadCategory>
}