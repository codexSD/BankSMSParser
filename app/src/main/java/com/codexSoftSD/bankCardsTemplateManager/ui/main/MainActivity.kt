package com.codexSoftSD.bankCardsTemplateManager.ui.main

import android.os.Bundle
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.ui.addTemplate.AddTemplateFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseActivity
import com.codexSoftSD.bankCardsTemplateManager.ui.purchases.PurchasesFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.templates.TemplateFragment


class MainActivity : BaseActivity<MainActivityPresenter>(),MainActivityView{
    override val TAG: String = "MainActivity"
    override val layoutId: Int = R.layout.activity_main
    override val presenter: MainActivityPresenter = MainActivityPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
//
//    override fun onBackPressed() {
////        super.onBackPressed()
//    }
    override fun loadTemplatesFragment() {
        loadFragment(TemplateFragment())
    }

    override fun loadAddTemplateFragment(){
        loadFragment(AddTemplateFragment())
    }

    override fun loadPurchasesView() {
        loadFragment(PurchasesFragment())
    }

    override fun initializeUI() {
    }
}