package com.codexSoftSD.bankCardsTemplateManager.data.purchases

import com.codexSoftSD.bankCardsTemplateManager.MainApp.Companion.db
import com.codexSoftSD.bankCardsTemplateManager.MainApp.Companion.synchronized
import com.codexSoftSD.bankCardsTemplateManager.data.sms.Sms
import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateFieldType
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils
import java.lang.Exception
import java.util.concurrent.atomic.AtomicBoolean
import java.util.regex.Pattern

class PurchasesRepository {
    companion object{
        private var purchasesList :ArrayList<Purchases> = ArrayList()
        var purchaseLoaded: AtomicBoolean = AtomicBoolean(false)
        suspend fun load(){
            //db.purchasesDao().deleteAll()
            loadPurchaseFromDB()
            loadPurchaseFromSMS()
            purchaseLoaded.set(true)
        }

        private suspend fun loadPurchaseFromSMS() {
            var sms:List<Sms> = listOf()
            try {
                SmsPresenter.loadAllSmsMessages()
                sms = SmsPresenter.getSmsMessages()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            for (message in sms){
                var isLoaded = false
                for (purchase in purchasesList){
                    if(message.id.equals(""+purchase.smsId)){
                        isLoaded = true
                        break
                    }
                }
                if (isLoaded) continue
                var purchase = parsePurchase(message)
                if(purchase != null){
                    try{
                        purchase = db.purchasesDao().getById(db.purchasesDao().insertAll(purchase)[0])
                        purchasesList.add(purchase)
                    }
                    catch (e: Exception){
                        Utils.handleException(e)
                    }
                }
            }
        }

        private suspend fun parsePurchase(message: Sms):Purchases? {
            var p: Purchases?
            try{
//                var templates = TemplateManager.getAll()
                var templates = TemplateManager.getAllTemplates()
                for (template in templates){
                    if(!template.data.smsId.equals(message.address,ignoreCase = false))
                        continue
                    var matcher = Pattern.compile(template.getRegex()).matcher(message.msg)
                    if(matcher.find()){
                        p = Purchases()
                        var matcherGroup = 1
                        for (field in template.fieldsData){
                            when(field.fieldType){
                                TemplateFieldType.CARD-> p.card = matcher.group(matcherGroup++)!!
                                TemplateFieldType.ACCOUNT -> p.account = matcher.group(matcherGroup++)!!
                                TemplateFieldType.CURRENCY -> p.currency = matcher.group(matcherGroup++)!!
                                TemplateFieldType.AMOUNT -> p.amount = matcher.group(matcherGroup++)!!.toDouble()
                                TemplateFieldType.FROM -> p.from = matcher.group(matcherGroup++)!!
                                TemplateFieldType.DATE -> p.date= matcher.group(matcherGroup++)!!
                                else -> {}
                            }
                        }
                        p.smsId = message.id.toLong()
                        p.smsTitle = message.address
                        return p
                    }
                }
            }
            catch (e: Exception){
                Utils.handleException(e)
            }
            return null
        }

        private fun loadPurchaseFromDB() {
            synchronized {
                purchasesList = db.purchasesDao().getAll() as ArrayList<Purchases>
//                purchasesList.addAll(db.purchasesDao().getAll())
            }
        }
        public fun getEnabledPurchases():ArrayList<Purchases>{
            return purchasesList.filter { it.enabled } as ArrayList<Purchases>
        }
        public fun getDisabledPurchases():ArrayList<Purchases>{
            return purchasesList.filter { !it.enabled } as ArrayList<Purchases>
        }
        fun disablePurchase(pur: Purchases){
            db.purchasesDao().disable(pur.smsId)
        }
        fun enablePurchase(pur: Purchases){
            db.purchasesDao().enable(pur.smsId)
        }
    }
}