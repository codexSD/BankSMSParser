package com.codexSoftSD.bankCardsTemplateManager.ui.templates.pickSms

import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

interface PickSmsView:BaseView {
    fun loadSms()
    fun pickSms(index: Long)
}