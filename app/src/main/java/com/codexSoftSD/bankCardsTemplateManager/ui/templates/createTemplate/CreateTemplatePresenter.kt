package com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate

import com.codexSoftSD.bankCardsTemplateManager.data.db.TemplateFieldData
import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.data.template.Template
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateField
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateFieldType
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAccessor

class CreateTemplatePresenter(var templateId: Int, view: CreateTemplateView):BasePresenter<CreateTemplateView>(view){
    lateinit var template: Template
    var finalFields = arrayListOf<TemplateField>() //fields use to load highlighted text on ui - also for
    var fields = arrayListOf<TemplateField>()
    var smsIndex = -1

    override fun start() {
        super.start()
        view.showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch{
            TemplateManager.templates.clear()
            if(templateId == -1){
                template = Template()
                //todo pick sms

            }
            else{
                //todo load template
                template = TemplateManager.getAllTemplates()[templateId]
                fields = TemplateManager.parseTemplateFieldValues(template)
            }
            if(smsIndex>=0){
                val sms = SmsPresenter.getSmsMessages()[smsIndex]
                view.loadSms(sms)
            }
            else
                view.loadTemplate()
            view.hideLoadingDialog()
        }
    }

    fun getFinalFields(message: String):ArrayList<TemplateField>{
        val final = arrayListOf<TemplateField>()
        var instance = message
        for(field in fields){
            if(instance.contains(field.value)){
                val index = instance.indexOf(field.value,0,false)
//                var temp = TemplateField()
                if(index > 0){
//                    temp.fieldType = TemplateFieldType.SOLID
//                    temp.value = instance.substring(0,index)
//                    temp.regex = temp.findRegex()
                    final.add(TemplateField().apply {
                        this.fieldType = TemplateFieldType.SOLID
                        this.value = instance.substring(0,index)
                        this.regex = this.findRegex()
                    })
                }
                final.add(TemplateField().apply {
                    this.fieldType = field.fieldType
                    this.value = field.value
                    this.regex = this.findRegex()
                })

//                temp = TemplateField()
//                temp.fieldType = field.fieldType
//                temp.value = field.value
//                temp.regex = temp.findRegex()
//                final.add(temp)
                instance = instance.removeRange(0,index + field.value.length)
            }
        }
        if(instance.isNotEmpty()){
            final.add(TemplateField().apply {
                this.fieldType = TemplateFieldType.SOLID
                this.value = instance
                this.regex = this.findRegex()
            })
        }
        return final
    }

    fun loadFinalFields(message: String){
        finalFields.clear()
        var instance = message
        for(field in fields){
            if(instance.contains(field.value)){
                val index = instance.indexOf(field.value,0,false)
                if(index > 0){
                    val temp = TemplateField()
                    temp.fieldType = TemplateFieldType.SOLID
                    temp.regex = instance.substring(0,index)
                    temp.value = temp.regex
                    finalFields.add(temp)
                }
                finalFields.add(field)
                instance = instance.removeRange(0,index + field.value.length)
            }
        }
    }
    fun deleteTemplateField(position: Int){
        view.log("deleteTemplateField: position: $position")
        fields.removeAt(position)
        view.updateFields()
        //todo delete template field at position
    }

    fun bindTemplateDataField(holder: TemplateFieldViewHolder,position: Int){
        holder.bind(fields[position])
    }

    fun setTemplateDataFieldType(position: Int,type: Int){
        view.log("setTemplateDataFieldType: type: $type")
        fields[position].fieldType = TemplateFieldType.values()[type]
        view.updateMessage()
    }
    fun setTemplateDataFieldValue(position: Int,value:String){
        view.log("setTemplateDataFieldValue: value: $value")
        fields[position].value = value
        view.updateMessage()
        //todo set data field value and update the message on fragment to highlight matched text with regex
    }

    fun addNewField() {
        view.log("addNewField: ")
        fields.add(TemplateField(TemplateFieldType.ADDITIONAL_INFO))
//        view.updateMessage()
        view.updateFields()
    }

    fun saveTemplate() {
        CoroutineScope(Dispatchers.IO).launch {
            view.showLoadingDialog()
            val sender = view.getMessageSender()
            val message = view.getMessage()
            val template = Template()
            template.data.smsId = sender
            template.data.smsMessage = message
            if(templateId == -1)//new Template
                template.data.templateId = TemplateManager.getNewTemplateId()
            else
                template.data.templateId = TemplateManager.getAllTemplates()[templateId].data.templateId

            var i = 0
            for (field in getFinalFields(message)){
                template.fieldsData.add(TemplateFieldData().apply {
                    this.fieldType = field.fieldType
                    this.regex = field.regex
                })
            }
            TemplateManager.saveTemplate(template)
            view.hideLoadingDialog()
            view.close()
        }
    }
}