package com.jg.QrBusinessCard.DbHelper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jg.QrBusinessCard.Models.BusinessCard

@Dao
interface BusinessCardDao {

    @Insert
    suspend fun insertBusinessCard(businessCard: BusinessCard)

    @Query("SELECT * FROM business_cards")
    suspend fun getAllBusinessCards(): List<BusinessCard>
}