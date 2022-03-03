package com.codexSoftSD.bankCardsTemplateManager.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TemplateDao {

    @Query("SELECT * FROM TemplateData")
    fun getAll():List<TemplateData>

    @Query("SELECT * FROM TemplateData WHERE templateId IN (:id)")
    fun getByTemplateId(id: Long):TemplateData

    @Insert
    fun insertAll(vararg template: TemplateData):List<Long>

    @Query("DELETE FROM TemplateData WHERE templateId = (:id)")
    fun delete(id: Long)

    @Query("DELETE FROM TemplateData")
    fun deleteAll()
}