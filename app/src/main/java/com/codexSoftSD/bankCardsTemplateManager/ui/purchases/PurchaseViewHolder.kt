package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.TotalPurchases

interface IPurchaseViewHolder {
    fun bindItem(purchase: Purchases)
}
interface ITotalPurchaseViewHolder {
    fun bindItem(total: TotalPurchases)
}