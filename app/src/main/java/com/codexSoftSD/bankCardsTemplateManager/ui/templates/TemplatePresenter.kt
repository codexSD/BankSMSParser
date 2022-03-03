package com.codexSoftSD.bankCardsTemplateManager.ui.templates

import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.data.template.Template
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TemplatePresenter(view: TemplateView) : BasePresenter<TemplateView>(view) {

    val templates: ArrayList<Template> = arrayListOf()
    override fun start() {
        super.start()
        CoroutineScope(Dispatchers.IO).launch {
            view.showLoadingDialog()
            TemplateManager.templates.clear()
            templates.addAll(TemplateManager.getAllTemplates())
            view.refreshTemplates()
            view.hideLoadingDialog()
        }
    }
    fun templateCount():Int{
        return templates.size
    }
    fun bindTemplateHolder(holderView: TemplateHolderView,position: Int){
        holderView.bindTemplate(templates[position])
    }
    fun onTemplateSelected(position: Int){
        view.loadTemplate(position)
        //todo open template
    }
    fun createNewTemplate(){
        view.createTemplate()
        //todo open new template
    }
}