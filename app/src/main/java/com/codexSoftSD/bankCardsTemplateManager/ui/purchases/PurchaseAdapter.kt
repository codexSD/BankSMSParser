package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R


class PurchaseAdapter (private val presenter:PurchasesPresenter): RecyclerView.Adapter<PurchaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.purchase_list_item_layout, parent,false)
        return PurchaseViewHolder(presenter,listItem)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        presenter.onBindItemView(holder,position)
    }

    override fun getItemCount(): Int {
        return presenter.purchasesCount
    }

}
class TotalPurchasesAdapter (private val presenter:PurchasesPresenter): RecyclerView.Adapter<TotalPurchaseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalPurchaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.purchases_total_list_item_layout, parent,false)
        return TotalPurchaseViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: TotalPurchaseViewHolder, position: Int) {
        presenter.onTotalPurchasesBindItemView(holder,position)
    }

    override fun getItemCount(): Int {
        return presenter.purchasesTotalCount
    }
}
