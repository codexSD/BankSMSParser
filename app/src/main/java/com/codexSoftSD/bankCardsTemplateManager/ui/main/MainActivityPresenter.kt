package com.codexSoftSD.bankCardsTemplateManager.ui.main

import com.codexSoftSD.bankCardsTemplateManager.ui.base.BasePresenter

class MainActivityPresenter(view: MainActivityView) : BasePresenter<MainActivityView>(view) {

    override fun start() {
        super.start()
        view.loadPurchasesView()
//        view.loadTemplatesFragment()
//        view.loadAddTemplateFragment()
//        view.requestPermission(object : OnPermissionResult {
//            override fun onGranted() {
//                view.loadAddTemplateFragment()
//            }
//
//            override fun onDenied() {
//                view.toast("Failed to grant permissions ... exiting")
//                Handler().postDelayed({ exitProcess(0) },5000)
//            }
//        }, Permissions.SMS)
    }

}