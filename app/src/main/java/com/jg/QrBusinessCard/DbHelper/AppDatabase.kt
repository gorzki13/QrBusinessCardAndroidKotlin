package com.jg.QrBusinessCard.DbHelper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jg.QrBusinessCard.Models.BusinessCard

@Database(entities = [BusinessCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessCardDao(): BusinessCardDao
}