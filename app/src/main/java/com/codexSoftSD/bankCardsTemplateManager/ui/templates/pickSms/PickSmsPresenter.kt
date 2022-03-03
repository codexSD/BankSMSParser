package com.codexSoftSD.bankCardsTemplateManager.ui.templates.pickSms

import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PickSmsPresenter(view: PickSmsView) :BasePresenter<PickSmsView>(view) {
    val text = arrayListOf<String>()
    var isNowShowAddress = true
    private var selectedAddressIndex = ""
    override fun start() {
        super.start()
        text.addAll(SmsPresenter.hashMap.keys)
        view.loadSms()
    }
    fun bindHolder(holder: SmsViewHolder, position: Int) {
        holder.bind(text[position])
    }
    fun selectSms(position: Int){
        CoroutineScope(Dispatchers.IO).launch {
            text.clear()
            if(selectedAddressIndex.isEmpty()){
                selectedAddressIndex = SmsPresenter.hashMap.keys.elementAt(position)
                for(index in SmsPresenter.hashMap[selectedAddressIndex]!!)
                    text.add(SmsPresenter.getSmsMessages()[index.toInt()].msg)
                view.loadSms()
            }
            else{
                val index = SmsPresenter.hashMap[selectedAddressIndex]!![position]
                view.pickSms(index)
            }
        }
    }
}