package com.codexSoftSD.bankCardsTemplateManager.ui.addTemplate

import android.content.ContentResolver
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

interface AddTemplateView:BaseView {
    fun getContentResolver():ContentResolver
}