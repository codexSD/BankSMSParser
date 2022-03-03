package com.codexSoftSD.bankCardsTemplateManager.ui.templates

import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

interface TemplateView:BaseView {
    fun setTemplateCount()
    fun refreshTemplates()
    fun loadTemplate(i: Int)
    fun createTemplate()
}