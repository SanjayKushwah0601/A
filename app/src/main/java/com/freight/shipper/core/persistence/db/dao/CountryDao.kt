package com.freight.shipper.core.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.model.Country
import com.freight.shipper.model.State

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountryList(values: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStateList(values: List<State>)

    @Query("SELECT * FROM country")
    fun getCountries(): List<Country>

    @Query("SELECT * FROM country")
    fun getCountriesLiveData(): LiveData<List<Country>>

    @Query("SELECT * FROM state WHERE state.countryId = (:countryId)")
    fun getStateLiveData(countryId: String): LiveData<List<State>>
}