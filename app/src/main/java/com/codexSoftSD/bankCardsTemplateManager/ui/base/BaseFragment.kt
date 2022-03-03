package com.codexSoftSD.bankCardsTemplateManager.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codexSoftSD.bankCardsTemplateManager.utils.OnPermissionResult
import com.codexSoftSD.bankCardsTemplateManager.utils.Permissions

abstract class BaseFragment<T: BasePresenter<*>>: Fragment(),BaseView {
    protected abstract val layoutId : Int
    protected abstract val presenter : T
    protected abstract val TAG:String

    protected var baseActivity: BaseActivity<*>? = null

    abstract fun initializeUI(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.initialize(arguments)
        return inflater.inflate(layoutId,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*>){
            baseActivity = context
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }
    override fun onDestroyView() {
        presenter.finalizeView()
        super.onDestroyView()
    }

    override fun toast(message: String) {
        baseActivity?.toast(message)
    }

    override fun log(message: String){
        Log.i(TAG, message)
    }
    override fun showSnack(message: String){
        baseActivity?.showSnack(message)
    }


    override fun showLoadingDialog() {
        baseActivity?.showLoadingDialog()
    }

    override fun hideLoadingDialog() {
        baseActivity?.hideLoadingDialog()
    }
}