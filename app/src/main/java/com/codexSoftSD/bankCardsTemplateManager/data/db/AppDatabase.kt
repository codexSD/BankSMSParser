package com.codexSoftSD.bankCardsTemplateManager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases

@Database(entities = [Purchases::class,TemplateData::class,TemplateFieldData::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun purchasesDao():PurchaseDao
    abstract fun templateDao():TemplateDao
    abstract fun templateFieldDao():TemplateFieldDao
}