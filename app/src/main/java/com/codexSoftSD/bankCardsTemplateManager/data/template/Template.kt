package com.codexSoftSD.bankCardsTemplateManager.data.template

import com.codexSoftSD.bankCardsTemplateManager.data.db.TemplateData
import com.codexSoftSD.bankCardsTemplateManager.data.db.TemplateFieldData

class Template
{
    var data: TemplateData = TemplateData("")
    var fieldsData:ArrayList<TemplateFieldData> = ArrayList()

//    var fields:ArrayList<TemplateField> = ArrayList();
    fun getRegex(): String {
        val str = StringBuilder()
        for (field in fieldsData) {
            if (field.fieldType!= TemplateFieldType.SOLID){
                str.append("("+field.regex+")")// () for group regex matching
            }
            else
                str.append(field.regex)
        }
        return str.toString()
    }

    override fun toString(): String {
        val str = StringBuilder()
        str.append("{")
        for (field in fieldsData) {
            str.append(field.toString()).append(",")
        }
        str.deleteCharAt(str.length - 1)
        str.append("}")
        return str.toString()
    }
}