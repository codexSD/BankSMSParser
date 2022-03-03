package com.codexSoftSD.bankCardsTemplateManager.ui.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.airbnb.lottie.LottieAnimationView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.utils.OnPermissionResult
import com.codexSoftSD.bankCardsTemplateManager.utils.Permissions
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import kotlin.system.exitProcess


abstract class BaseActivity<T: BasePresenter<*>> : AppCompatActivity(), BaseView {
    protected abstract val TAG:String
    protected abstract  val layoutId: Int
    protected abstract  val presenter: T
    private lateinit var animationView: LottieAnimationView;
    lateinit var loadingDialog:Dialog;
    abstract fun initializeUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        loadingDialog = Dialog(this@BaseActivity)
        loadingDialog.setCancelable(false)
        val view =LayoutInflater.from(this@BaseActivity).inflate(R.layout.loading_dialog,null,false)
        loadingDialog.setContentView(view)
        loadingDialog.window?.setBackgroundDrawableResource(R.color.transparent)

        presenter.initialize(intent.extras)
        initializeUI()
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.finalizeView()
    }

    override fun toast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun showSnack(message: String){
        try {
            Snackbar.make(window.decorView.rootView,message,Snackbar.LENGTH_LONG).show()
        }
        catch (e: Exception){
            Utils.handleException(e)
        }
    }


    override fun showLoadingDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            try{
                loadingDialog.show()
            }
            catch(e: Exception){
                Utils.handleException(e)
            }
        }
//        animationView.playAnimation()
    }

    override fun hideLoadingDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            try{
                loadingDialog.dismiss()
            }
            catch(e: Exception){
                Utils.handleException(e)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onBackPressed() {
//        log("fragments count: "+supportFragmentManager.fragments.size)
        if(supportFragmentManager.fragments.size > 1){
            removeFragment(supportFragmentManager.fragments.last())
        }
        else{
//            finish()
            exitProcess(0)
        }

//        supportFragmentManager.popBackStackImmediate()
//        super.onBackPressed()
    }
    private fun removeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().remove(fragment).commit()
//        val tr : FragmentTransaction = supportFragmentManager.beginTransaction()
//        tr.remove(fragment)
//        tr.commit()
    }
    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
    }
    fun replaceFragment(oldFragment: Fragment,newFragment: Fragment){
        removeFragment(oldFragment)
        loadFragment(newFragment)
    }
    fun loadFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().add(R.id.frameLayout,fragment).commit()
//        val tr : FragmentTransaction = supportFragmentManager.beginTransaction()
//        tr.add(R.id.frameLayout,fragment)
////        tr.replace(R.id.frameLayout,fragment)
//        tr.commit()
    }
    override fun log(log:String){
        Log.i(TAG, "log: $log")
    }
}