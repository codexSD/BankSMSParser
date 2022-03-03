package com.codexSoftSD.bankCardsTemplateManager.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun getAll():List<Purchases>

    @Query("SELECT * FROM purchases WHERE smsId IN (:smsId)")
    fun getBySmsId(smsId: Long):List<Purchases>

    @Query("SELECT * FROM purchases WHERE uid IN (:id)")
    fun getById(id: Long):Purchases

    @Insert
    fun insertAll(vararg purchases: Purchases):List<Long>
    @Insert
    fun insert(purchases: Purchases):Long

    @Delete
    fun delete(purchases: Purchases)

    @Query("DELETE FROM purchases")
    fun deleteAll()
    @Query("UPDATE purchases SET isEnabled = 0 where smsId=:smsId")
    fun disable(smsId:Long)

    @Query("UPDATE purchases SET isEnabled = 1 where smsId=:smsId")
    fun enable(smsId:Long)
}