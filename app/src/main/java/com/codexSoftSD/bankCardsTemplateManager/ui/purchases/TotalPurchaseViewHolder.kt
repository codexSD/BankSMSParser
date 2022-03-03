package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.TotalPurchases

class TotalPurchaseViewHolder(itemView: View) : ViewHolder(itemView),ITotalPurchaseViewHolder {
    val amount: TextView = itemView.findViewById(R.id.tv_total_purchase_amount)
    val currency: TextView = itemView.findViewById(R.id.tv_total_purchase_currency)
    @SuppressLint("SetTextI18n")
    override fun bindItem(total: TotalPurchases) {
        amount.text = ""+total.amount
        currency.text = ""+total.currency
    }
}


class PurchaseViewHolder(private val presenter: PurchasesPresenter, itemView: View) : ViewHolder(itemView),IPurchaseViewHolder {
    private var isExpanded = true
    private val smsId: TextView = itemView.findViewById(R.id.tv_sms_id)
    private val expandImageView: ImageView = itemView.findViewById(R.id.iv_expand)
    private val from: TextView = itemView.findViewById(R.id.tv_from)
    private val amount: TextView = itemView.findViewById(R.id.tv_amount)
    private val currency: TextView = itemView.findViewById(R.id.tv_currency)
    private val cardNumber: TextView = itemView.findViewById(R.id.tv_card_number)
    private val accountNumber: TextView = itemView.findViewById(R.id.tv_account_number)
    private val date: TextView = itemView.findViewById(R.id.tv_date)
    private val container : ViewGroup = itemView.findViewById(R.id.container)
    private val delete : ViewGroup = itemView.findViewById(R.id.delete_container)
    private val viewMessage : ViewGroup = itemView.findViewById(R.id.view_message_container)

    init{
        delete.setOnClickListener { presenter.deleteItem(adapterPosition) }
        viewMessage.setOnClickListener { presenter.viewSms(adapterPosition) }
        expandOrHide()
        container.setOnClickListener { expandOrHide() }
    }
    @SuppressLint("SetTextI18n")
    override fun bindItem(purchase: Purchases) {
        from.text = purchase.from
        amount.text = ""+purchase.amount
        accountNumber.text = purchase.account
        cardNumber.text = purchase.card
        currency.text = purchase.currency
        smsId.text = purchase.smsTitle
        date.text = purchase.date
    }
    private fun expandOrHide(){
        if(isExpanded){
            viewMessage.visibility = View.GONE
            delete.visibility = View.GONE
            expandImageView.setImageResource(R.drawable.down_arrow)
        }
        else{
            viewMessage.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
            expandImageView.setImageResource(R.drawable.up_arrow)
        }
        isExpanded =! isExpanded
    }
}