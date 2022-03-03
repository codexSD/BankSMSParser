package com.codexSoftSD.bankCardsTemplateManager.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TemplateFieldDao {

    @Query("SELECT * FROM TemplateFieldData")
    fun getAll():List<TemplateFieldData>

    @Query("SELECT * FROM TemplateFieldData WHERE parent_id IN (:parentId)")
    fun getByTemplateId(parentId: Long):List<TemplateFieldData>

    @Insert
    fun insertAll(vararg templateFieldData: TemplateFieldData)

    @Delete
    fun delete(templateFieldData: TemplateFieldData)
}