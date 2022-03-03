package com.codexSoftSD.bankCardsTemplateManager.ui.main

import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

interface MainActivityView:BaseView {
    fun loadTemplatesFragment()
    fun loadAddTemplateFragment()
    fun loadPurchasesView()
}