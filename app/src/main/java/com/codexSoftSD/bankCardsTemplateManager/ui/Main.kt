package com.codexSoftSD.bankCardsTemplateManager.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.sms.SmsPresenter
import com.codexSoftSD.bankCardsTemplateManager.ui.main.MainActivity
import com.codexSoftSD.bankCardsTemplateManager.utils.OnPermissionResult
import com.codexSoftSD.bankCardsTemplateManager.utils.Permissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions

class Main : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        requestPermission(object : OnPermissionResult {
            override fun onGranted() {
                CoroutineScope(Dispatchers.IO).launch { SmsPresenter.loadAllSmsMessages() } //pre-loading all sms messages
                startActivity(Intent(baseContext,MainActivity::class.java))
                finish()
            }

            override fun onDenied() {
                CoroutineScope(Dispatchers.IO).launch {
                    Toast.makeText(baseContext, "فشل جلب الصلاحيان", Toast.LENGTH_LONG).show()
                    delay(5000)
                    finish()
                }
            }
        },Permissions.SMS)

    }

    private var onPermissionResult: OnPermissionResult? = null

    fun requestPermission(onResult: OnPermissionResult, vararg  permissions : Permissions){
        val permissionsRequest : Array<String> = permissions.map { it.permission }.toTypedArray()
        var relational :String  = "Please Grant The Required Permissions"
        permissions.forEach {
            relational += "\n"+it.relational
        }
        if(EasyPermissions.hasPermissions(this, *permissionsRequest)){
            onResult.onGranted()
        }
        else{
            onPermissionResult = onResult;
            EasyPermissions.requestPermissions(this,relational,100,*permissionsRequest)
        }
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        onPermissionResult?.onGranted()

    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        onPermissionResult?.onDenied()
    }
}