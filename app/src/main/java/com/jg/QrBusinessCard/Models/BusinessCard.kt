package com.jg.QrBusinessCard.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_cards")
data class BusinessCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val number: Int,
    val email: String
)