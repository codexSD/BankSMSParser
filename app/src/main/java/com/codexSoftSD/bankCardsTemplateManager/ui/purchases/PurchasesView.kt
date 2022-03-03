package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseView

    interface PurchasesView:BaseView {
    fun loadPurchases(purchases: List<Purchases>)
    fun notifyPurchasesChanged()
    fun askForPurchaseDisable()
    fun disablePurchase()
}