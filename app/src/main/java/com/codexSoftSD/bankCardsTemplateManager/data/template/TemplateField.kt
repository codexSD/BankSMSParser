package com.codexSoftSD.bankCardsTemplateManager.data.template

import com.codexSoftSD.bankCardsTemplateManager.utils.DateUtil
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils

class TemplateField(
    var fieldType :TemplateFieldType = TemplateFieldType.SOLID,
    var regex :String = ""
){
    var value = ""
    fun findRegex():String{
        var result = ""
        try{
            result = when (fieldType){
                TemplateFieldType.SOLID -> value
                TemplateFieldType.AMOUNT -> "\\d+.?\\d+"
                TemplateFieldType.ON_ACCOUNT -> "\\d+.?\\d+"
                TemplateFieldType.CURRENCY -> "\\w{1,${value.length}}"
                TemplateFieldType.ACCOUNT -> "\\w{1,${value.length}}"
                TemplateFieldType.CARD -> "\\w{1,${value.length}}"
                TemplateFieldType.FROM -> "\\w+"
                TemplateFieldType.DATE -> DateUtil.getRegex(value)
                TemplateFieldType.ADDITIONAL_INFO -> "\\w+"
            }
        }
        catch(e: Exception){
            Utils.handleException(e)
        }
        return result;
    }
}
