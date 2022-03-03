package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.PurchasesRepository
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.TotalPurchases
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchasesPresenter(view: PurchasesView): BasePresenter<PurchasesView>(view) {
    var purchases: ArrayList<Purchases> = arrayListOf()
    var totalPurchases: ArrayList<TotalPurchases>  = arrayListOf()
    val purchasesTotalCount: Int get() = totalPurchases.size
    val purchasesCount: Int get() = purchases.size

    override fun start() {
        super.start()

        view.showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch {
            loadPurchases()
            CoroutineScope(Dispatchers.Main).launch {
                view.hideLoadingDialog()
            }
        }

    }

    private suspend fun loadPurchases() {

//        val fields: ArrayList<TemplateField> = ArrayList()
//        fields.add(TemplateField(TemplateFieldType.SOLID,"شراء عبر نقاط البيع مدى أثير\n"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "مبلغ: "))
//        fields.add(TemplateField(TemplateFieldType.AMOUNT, "\\d+"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, " "))
//        fields.add(TemplateField(TemplateFieldType.CURRENCY, "\\w{1,3}"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "بطاقة مدى: "))
//        fields.add(TemplateField(TemplateFieldType.CARD, "\\w{1,4}"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "\\*\n"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "حساب: \\*\\*"))
//        fields.add(TemplateField(TemplateFieldType.ACCOUNT, "\\w{1,4}"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "من: "))
//        fields.add(TemplateField(TemplateFieldType.FROM, "\\w+"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "\n"))
//        fields.add(TemplateField(TemplateFieldType.SOLID, "في: "))
//        fields.add(TemplateField(TemplateFieldType.DATE, "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}"))
//        TemplateManager.deleteAll()
//        TemplateManager.insert("AThER",fields)
//        TemplateManager.updateTemplates()
        PurchasesRepository.load()
        while (!PurchasesRepository.purchaseLoaded.get())
            delay(1000)

        purchases.clear()
        totalPurchases.clear()
        totalPurchases.add(TotalPurchases(0.0,"SAR"))
        while (!PurchasesRepository.purchaseLoaded.get())
            delay(1000)

        addPurchases(PurchasesRepository.getEnabledPurchases())
    }

    fun onItemClicked(pos: Int){

    }
    fun deleteItem(pos: Int){
        view.showLoadingDialog()
        CoroutineScope(Dispatchers.IO).launch{
            PurchasesRepository.disablePurchase(purchases[pos])
            loadPurchases()

            CoroutineScope(Dispatchers.Main).launch {
                view.hideLoadingDialog()
            }
        }
    }
    fun viewSms(pos: Int){

    }
    fun onBindItemView(view: IPurchaseViewHolder, pos: Int){
        view.bindItem(purchases[pos])
    }
    fun onTotalPurchasesBindItemView(view: ITotalPurchaseViewHolder, pos: Int){
        view.bindItem(totalPurchases[pos])
    }

    private fun addPurchases(purs:Collection< Purchases>){
        var isFound = false
        for (pur in purs){
            purchases.add(pur)
            isFound = false;
            for (total in totalPurchases){
                if (total.currency.equals(pur.currency,ignoreCase = true)){
                    total.amount += pur.amount
                    isFound = true
                    break
                }
            }
            if(!isFound){
                totalPurchases.add(TotalPurchases( amount = pur.amount,currency = pur.currency ))
            }
        }
        CoroutineScope(Dispatchers.Main).launch { view.notifyPurchasesChanged() }
    }

}