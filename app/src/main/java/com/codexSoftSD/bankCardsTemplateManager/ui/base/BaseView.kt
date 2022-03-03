package com.codexSoftSD.bankCardsTemplateManager.ui.base

import com.codexSoftSD.bankCardsTemplateManager.utils.OnPermissionResult
import com.codexSoftSD.bankCardsTemplateManager.utils.Permissions

interface BaseView {
    fun toast(message: String)
    fun log(message: String)
    fun showSnack(message: String)
    fun showLoadingDialog()
    fun hideLoadingDialog()
//    fun requestPermission(onResult: OnPermissionResult, vararg permissions : Permissions)
}