package com.codexSoftSD.bankCardsTemplateManager.ui.templates

import com.codexSoftSD.bankCardsTemplateManager.data.template.Template

interface TemplateHolderView {
    fun bindTemplate(template: Template)
//    fun setOnClickListener(l:View.OnClickListener)
}