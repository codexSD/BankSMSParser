package com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateField
import com.google.android.material.textfield.TextInputEditText

class TemplateFieldsAdapter(val presenter: CreateTemplatePresenter): RecyclerView.Adapter<TemplateFieldsAdapter.ViewHolder>() {

    class ViewHolder(val presenter: CreateTemplatePresenter,itemView: View) : RecyclerView.ViewHolder(itemView),TemplateFieldViewHolder {
        private val type:AutoCompleteTextView = itemView.findViewById(R.id.tv_template_field_type)
        private val value:TextInputEditText = itemView.findViewById(R.id.tv_template_field_value)
        private val delete: ViewGroup = itemView.findViewById(R.id.delete_container)

        override fun bind(field: TemplateField) {
            value.setText(field.value)
            val strings = itemView.resources.getStringArray(R.array.TemplateFieldTypes);
            val adapter = ArrayAdapter(itemView.context, R.layout.list_item, strings)
            type.setAdapter(adapter)
            type.setText(adapter.getItem(field.fieldType.ordinal),false)
//            type.setSelection(field.fieldType.ordinal)
            delete.setOnClickListener {
                presenter.deleteTemplateField(adapterPosition)
            }
            value.doAfterTextChanged {
                presenter.setTemplateDataFieldValue(adapterPosition,it.toString())
            }
            type.setOnItemClickListener { adapterView, view, i, l ->
                presenter.setTemplateDataFieldType(adapterPosition,i)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(presenter,LayoutInflater.from(parent.context).inflate(R.layout.list_item_template_fields,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindTemplateDataField(holder,position)
    }

    override fun getItemCount(): Int {
        return presenter.fields.size
    }

}
