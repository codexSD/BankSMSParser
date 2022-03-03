package com.codexSoftSD.bankCardsTemplateManager.data.template

import com.codexSoftSD.bankCardsTemplateManager.MainApp
import com.codexSoftSD.bankCardsTemplateManager.MainApp.Companion.db
import com.codexSoftSD.bankCardsTemplateManager.data.db.TemplateData
import com.codexSoftSD.bankCardsTemplateManager.data.db.TemplateFieldData
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.asDeferred
import java.util.concurrent.atomic.AtomicBoolean
import java.util.regex.Pattern
import javax.xml.transform.Templates

class TemplateManager {
    companion object{

        val templates: ArrayList<Template> = ArrayList()
        private val isLoading = AtomicBoolean(false)
        fun insertTemplate(smsId:String,fields:ArrayList<TemplateField>):Template{
            var template = Template()
            var templateId = 0L
            var templateData = TemplateData()
            templateData.smsId = smsId
            var id = db.templateDao().insertAll(templateData)[0]
            template.data = db.templateDao().getByTemplateId(id)


            var dataFields:ArrayList<TemplateFieldData> = ArrayList()
            for(field in fields){
                dataFields.add(TemplateFieldData().apply {
                    this.fieldType = field.fieldType
                    this.regex = field.regex
                    this.parentId = template.data.templateId
                })
            }
            db.templateFieldDao().insertAll(*dataFields.toTypedArray())
            //template.fieldsData = db.templateFieldDao().getByTemplateId(template.data.templateId).toCollection(template.fieldsData)
            db.templateFieldDao().getByTemplateId(template.data.templateId).toCollection(template.fieldsData)
            return template
        }
        fun insert(template: Template){

        }
        fun insert(smsId: String,fields: ArrayList<TemplateField>):Template{
            var template = Template()
            var templateData = TemplateData()
            templateData.smsId = smsId
            var id = db.templateDao().insertAll(templateData)[0]
            template.data = db.templateDao().getByTemplateId(id)


            var dataFields:ArrayList<TemplateFieldData> = ArrayList()
            for(field in fields){
                dataFields.add(TemplateFieldData().apply {
                    this.fieldType = field.fieldType
                    this.regex = field.regex
                    this.parentId = template.data.templateId
                })
            }
            db.templateFieldDao().insertAll(*dataFields.toTypedArray())
            db.templateFieldDao().getByTemplateId(template.data.templateId).toCollection(template.fieldsData)
            return template
        }
        fun getAll():ArrayList<Template>{
            val templates:ArrayList<Template> = ArrayList()
            val templatesData = db.templateDao().getAll()
            for (data in templatesData){
                templates.add(Template().apply {
                    this.data = data
                    db.templateFieldDao().getByTemplateId(data.templateId).toCollection(this.fieldsData)
                })
            }
            return templates
        }
        fun deleteAll(){
            db.templateDao().deleteAll()
        }

        suspend fun updateTemplates(){
            deleteAll()

            for (template in getAllTemplatesFromFirebase()){
                var id = db.templateDao().insertAll(template.data)[0]
                template.data = db.templateDao().getByTemplateId(id)
                for (field in template.fieldsData){
                    field.parentId = template.data.templateId
                }
                db.templateFieldDao().insertAll(*template.fieldsData.toTypedArray())
                db.templateFieldDao().getByTemplateId(template.data.templateId).toCollection(template.fieldsData)
            }
//            val result: DataSnapshot = MainApp.fireDatabase.reference.child("Templates").get().asDeferred().await()
//            val fields: ArrayList<TemplateField> = ArrayList()
//            for(template in result.children){
//                var name:String? = ""
//                var message = ""
//                fields.clear()
//                for(field in template.children){
//                    when (field.key){
//                        "Name" -> name = field.value as String?
//                        "SMS" -> message = field.value as String
//                        else ->{
//                            val tempField = TemplateField()
//                            tempField.regex = field.child("Regex").value.toString()
//                            tempField.fieldType = TemplateFieldType.values()[(field.child("Type").value as Long).toInt()]
//                            fields.add(tempField)
//
//                        }
//                    }
//                }
//                insert(name?:"",fields)
//            }
        }

        suspend fun parseTemplateFieldValues(template: Template):ArrayList<TemplateField>{
            val fields:ArrayList<TemplateField> = arrayListOf()
            try{
//                val message = "شراء عبر نقاط البيع مدى أثير\n" +
//                        "مبلغ: 30 SAR\n" +
//                        "بطاقة مدى: 2623*\n" +
//                        "حساب: **3000\n" +
//                        "من: SAHAL\n" +
//                        "في: 2022-1-1 07:54"
                val matcher = Pattern.compile(template.getRegex()).matcher(template.data.smsMessage)
//                val isMathced = message.equals(template.data.smsMessage,ignoreCase = true)
//                val matcher = Pattern.compile(template.getRegex()).matcher(message)
                if(matcher.find()){
                    var matcherGroup = 1
                    for(dataField in template.fieldsData){

                        if(dataField.fieldType == TemplateFieldType.SOLID)
                            continue //skip solid info

                        val field = TemplateField()
                        field.fieldType = dataField.fieldType
                        field.regex = dataField.regex
                        field.value = matcher.group(matcherGroup++)?:""
                        fields.add(field)
                    }
                }
            }
            catch(e: Exception){
                Utils.handleException(e)
            }
            return fields
        }
        suspend fun saveTemplate(template:Template){
            try{

                val tRef = MainApp.fireDatabase.reference.child("Templates").child("${template.data.templateId}")
                tRef.removeValue().asDeferred().await()
                tRef.child("Name").setValue(template.data.smsId).asDeferred().await()
                tRef.child("SMS").setValue(template.data.smsMessage.replace("\n","\\n")).asDeferred().await()
                for ( i in template.fieldsData.indices){
                    tRef.child("$i").child("Regex").setValue(template.fieldsData[i].regex.replace("\n","\\n").replace("\\*","*")).asDeferred().await()
                    tRef.child("$i").child("Type").setValue(template.fieldsData[i].fieldType.ordinal).asDeferred().await()
                }
            }
            catch(e: Exception){
                Utils.handleException(e)
            }
        }
        private suspend fun getAllTemplatesFromFirebase():ArrayList<Template>{
            if(templates.size > 0)
                return templates
            try{
                isLoading.set(true)
                val result: DataSnapshot = MainApp.fireDatabase.reference.child("Templates").get().asDeferred().await()
                for(templateChild in result.children){
                    try{
                        val template = Template()
                        var name = ""
                        var sms = ""
                        val id = templateChild.key!!
                        for(field in templateChild.children){
                            when (field.key) {
                                "Name" -> name = field.value as String
                                "SMS" -> sms = field.value as String
                                else -> {
                                    template.fieldsData.add(TemplateFieldData().apply {
                                        this.regex = field.child("Regex").value.toString().replace("\\n","\n").replace("*","\\*")
                                        this.fieldType = TemplateFieldType.values()[(field.child("Type").value as Long).toInt()]
                                        this.templateFieldId = (field.key?:"0").toLong()
                                        this.parentId = id.toLong()
                                    })
                                }
                            }
                        }
                        template.data.smsId = name
                        template.data.smsMessage = sms.replace("\\n","\n")
                        template.data.templateId = id.toLong()
                        templates.add(template)
                    }
                    catch (e: Exception){
                        Utils.handleException(e)
                    }
                }
            }
            catch (e:Exception){
                Utils.handleException(e)
            }
            finally {
                isLoading.set(false)
            }
            return templates
        }
        suspend fun getNewTemplateId():Long{
            var lastId = 0L
            for (template in getAllTemplates()){
                if(template.data.templateId > lastId)
                    lastId = template.data.templateId
            }
            return ++lastId
        }
        suspend fun getAllTemplates():ArrayList<Template>{
            while (isLoading.get())
                delay(100)
            return getAllTemplatesFromFirebase()
        }
    }
}