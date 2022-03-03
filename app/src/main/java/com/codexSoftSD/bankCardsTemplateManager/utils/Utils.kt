package com.codexSoftSD.bankCardsTemplateManager.utils

import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateFieldType
import java.lang.Exception

class Utils {
    companion object{
        public fun handleException(e: Exception){
            e.printStackTrace()
        }
    }
}