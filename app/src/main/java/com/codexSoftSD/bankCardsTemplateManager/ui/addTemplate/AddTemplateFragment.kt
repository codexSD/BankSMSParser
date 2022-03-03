package com.codexSoftSD.bankCardsTemplateManager.ui.addTemplate

import android.content.ContentResolver
import android.content.Context
import android.view.View
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseFragment


class AddTemplateFragment : BaseFragment<AddTemplatePresenter>(),AddTemplateView {

    override val layoutId = R.layout.fragment_add_template
    override lateinit var presenter: AddTemplatePresenter
    override val TAG: String = "AddTemplateFragment"
    override fun initializeUI(view: View) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = AddTemplatePresenter(this)
    }

    override fun getContentResolver(): ContentResolver {
        return baseActivity!!.contentResolver
    }


}