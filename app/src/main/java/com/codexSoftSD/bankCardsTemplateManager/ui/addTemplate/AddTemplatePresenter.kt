package com.codexSoftSD.bankCardsTemplateManager.ui.addTemplate

import com.codexSoftSD.bankCardsTemplateManager.data.purchases.PurchasesRepository
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateField
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateFieldType
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter
import kotlinx.coroutines.*

class AddTemplatePresenter(view: AddTemplateView) : BasePresenter<AddTemplateView>(view) {
    override fun start() {
        super.start()

        val fields: ArrayList<TemplateField> = ArrayList()
        fields.add(TemplateField(TemplateFieldType.SOLID,"شراء عبر نقاط البيع مدى أثير\n"))
        fields.add(TemplateField(TemplateFieldType.SOLID,""))
        fields.add(TemplateField(TemplateFieldType.SOLID, "مبلغ: "))
        fields.add(TemplateField(TemplateFieldType.AMOUNT, "\\d+"))
        fields.add(TemplateField(TemplateFieldType.SOLID, " "))
        fields.add(TemplateField(TemplateFieldType.CURRENCY, "\\w{1,3}"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "بطاقة مدى: "))
        fields.add(TemplateField(TemplateFieldType.CARD, "\\w{1,4}"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "\\*\n"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "حساب: " + "\\*\\*"))
        fields.add(TemplateField(TemplateFieldType.ACCOUNT, "\\w{1,4}"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "من: "))
        fields.add(TemplateField(TemplateFieldType.FROM, "\\w+"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
        fields.add(TemplateField(TemplateFieldType.SOLID, "في: "))
        fields.add(TemplateField(TemplateFieldType.DATE, "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}"))


//        val template = Template(0,"sms1",fields)
////        db.templateDao().insertAll(template)
////        var templates  = db.templateDao().getAll()

        view.showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch {
            TemplateManager.deleteAll()
            TemplateManager.insert("AThER",fields)
            PurchasesRepository.load()
            while (!PurchasesRepository.purchaseLoaded.get())
                delay(1000)

            view.log("all pruchases -------------------------------------------------------")
           for(pur in PurchasesRepository.getEnabledPurchases()){
               view.log(pur.toString())
           }
           CoroutineScope(Dispatchers.Main).launch {
                view.hideLoadingDialog()
            }
        }
    }
    override fun finalizeView() {
        super.finalizeView()


    }
}