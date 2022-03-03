package com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.sms.Sms
import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.templates.TemplateFragment
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTemplateFragment(var templateId:Int) : BaseFragment<CreateTemplatePresenter>(),CreateTemplateView {
//class CreateTemplateFragment() : BaseFragment<CreateTemplatePresenter>(),CreateTemplateView {
    override val layoutId = R.layout.fragment_create_template
    override var presenter: CreateTemplatePresenter =  CreateTemplatePresenter(templateId,this)
    override val TAG: String = "CreateTemplateFragment"
    var smsIndex = -1
    lateinit var smsSender: TextInputEditText
    lateinit var smsMessage: TextInputEditText
    lateinit var rvTemplateFields: RecyclerView
    lateinit var saveButton:Button
    lateinit var cancelButton:Button
    lateinit var addFieldButton:Button
    lateinit var templateFieldsAdapter:TemplateFieldsAdapter

    constructor(smsId:String) : this(-1) {
        smsIndex = smsId.toInt()
        presenter.smsIndex = smsIndex
    }
//    constructor(templateId: Int) : this() {
//        presenter =  CreateTemplatePresenter(templateId,this)
//    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        presenter = CreateTemplatePresenter(templateId,this)
    }
    override fun initializeUI(view: View) {
        smsSender = view.findViewById(R.id.ti_sms_sender)
        smsMessage = view.findViewById(R.id.ti_sms_message)

        rvTemplateFields = view.findViewById(R.id.rv_template_items)
        addFieldButton = view.findViewById(R.id.btn_add_field)
        addFieldButton.setOnClickListener {
            presenter.addNewField()
        }
        cancelButton = view.findViewById(R.id.btn_cancel)
        saveButton = view.findViewById(R.id.btn_save)
//        if(templateId >= 0){//not a new template
//            smsSender.isEnabled = false
//            smsMessage.isEnabled = false
//        }
//        smsMessage.doAfterTextChanged { updateMessage() }
        cancelButton.setOnClickListener {
            close()
        }
        saveButton.setOnClickListener {
            presenter.saveTemplate()
        }

        templateFieldsAdapter = TemplateFieldsAdapter(presenter)
        rvTemplateFields.layoutManager = LinearLayoutManager(requireContext())
        rvTemplateFields.adapter = templateFieldsAdapter
    }

    override fun close() {

//        baseActivity?.onBackPressed()
//        baseActivity?.loadFragment(TemplateFragment())
        baseActivity?.replaceFragment(TemplateFragment())
    }

    override fun loadSms(sms: Sms) {
        CoroutineScope(Dispatchers.Main).launch{
            smsSender.setText(sms.address)
            smsMessage.setText(sms.msg)
        }
    }
    override fun loadTemplate() {
        CoroutineScope(Dispatchers.Main).launch{
            smsSender.setText(presenter.template.data.smsId)
            smsMessage.setText(presenter.template.data.smsMessage)
            templateFieldsAdapter.notifyDataSetChanged()
            updateMessage()
        }
    }

    override fun getMessageSender(): String {
        return smsSender.text.toString()
    }
    override fun getMessage(): String {
        return smsMessage.text.toString()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun updateMessage() {
        CoroutineScope(Dispatchers.Main).launch{
            val text = smsMessage.text.toString()
            val spannableString = SpannableString(text)
            smsMessage.text?.clear()
//            for(field in presenter.finalFields){
            for(field in presenter.fields){
                try{
                    val index = text.indexOf(field.value)
                    if(index < 0)
                        continue
                    spannableString.setSpan(BackgroundColorSpan(resources.getColor(R.color.primaryLight)),index,index+field.value.length,0)
                }
                catch(e: Exception){
                    Utils.handleException(e)
                }
            }
            smsMessage.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }

    override fun updateFields() {
        templateFieldsAdapter.notifyDataSetChanged()
        updateMessage()
//        rvTemplateFields.postDelayed({ templateFieldsAdapter.notifyDataSetChanged() },100)
    }

}