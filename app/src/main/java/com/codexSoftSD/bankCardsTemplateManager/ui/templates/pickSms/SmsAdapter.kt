package com.codexSoftSD.bankCardsTemplateManager.ui.templates.pickSms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import java.util.zip.Inflater

class SmsAdapter(val presenter: PickSmsPresenter): RecyclerView.Adapter<SmsAdapter.ViewHolder>() {
    class ViewHolder(val presenter: PickSmsPresenter,itemView: View) : RecyclerView.ViewHolder(itemView),SmsViewHolder {
        val text: TextView = itemView.findViewById(R.id.tv_sms)
        override fun bind(sms:String) {
            this.text.text = sms
            itemView.setOnClickListener { presenter.selectSms(adapterPosition) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sms_holder,parent,false)
        return (ViewHolder(presenter,view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindHolder(holder,position)
    }

    override fun getItemCount(): Int {
        return presenter.text.size
    }
}