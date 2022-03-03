package com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate

import com.codexSoftSD.bankCardsTemplateManager.data.sms.Sms
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

interface CreateTemplateView:BaseView {
    fun loadTemplate()
    fun updateMessage()
    fun updateFields()
    fun getMessage():String
    fun getMessageSender():String
    fun loadSms(sms: Sms)
    fun close()
}