package com.freight.shipper.core.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountryList(values: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStateList(values: List<State>)

    @Query("SELECT * FROM country ORDER BY countryName")
    fun getCountries(): List<Country>

    @Query("SELECT * FROM country ORDER BY countryName")
    fun getCountriesLiveData(): LiveData<List<Country>>

    @Query("SELECT * FROM state WHERE state.countryId = (:countryId) ORDER BY stateName")
    fun getStateLiveData(countryId: String): LiveData<List<State>>
}