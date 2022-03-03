package com.codexSoftSD.bankCardsTemplateManager.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateFieldType

@Entity
data class TemplateData(
    @ColumnInfo(name = "sms_id") var smsId: String = ""
) {
    @PrimaryKey(autoGenerate = true) var templateId: Long= 0
    @ColumnInfo(name = "sms_message") var smsMessage: String = ""
}

@Entity
data class TemplateFieldData(
    @ColumnInfo(name = "field_type") var fieldType : TemplateFieldType = TemplateFieldType.SOLID,
    @ColumnInfo(name = "regex") var regex :String = ""

) {
    @PrimaryKey(autoGenerate = true) var templateFieldId: Long = 0
    @ColumnInfo(name = "parent_id") var parentId :Long = 0
}