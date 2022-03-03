package com.codexSoftSD.bankCardsTemplateManager.ui.templates

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.template.Template

class TemplatesAdapter(private val presenter: TemplatePresenter):RecyclerView.Adapter<TemplatesAdapter.ViewHolder>() {
    class ViewHolder(private val presenter: TemplatePresenter,itemView: View) :RecyclerView.ViewHolder(itemView),TemplateHolderView{
        private val tvId:TextView = itemView.findViewById(R.id.tv_template_id)
        private val tvName:TextView = itemView.findViewById(R.id.tv_template_name)
        private val tvMessage:TextView = itemView.findViewById(R.id.tv_template_message)
        private val container:ViewGroup = itemView.findViewById(R.id.container)
        @SuppressLint("SetTextI18n")
        override fun bindTemplate(template: Template) {
            tvId.text = ""+template.data.templateId
            tvName.text = template.data.smsId
            tvMessage.text = template.data.smsMessage
            container.setOnClickListener { presenter.onTemplateSelected(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_list_item,parent,false)
        return ViewHolder(presenter,view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindTemplateHolder(holder,position)
    }

    override fun getItemCount(): Int {
        return presenter.templateCount()
    }
}