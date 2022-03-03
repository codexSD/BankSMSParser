package com.codexSoftSD.bankCardsTemplateManager.ui.templates.pickSms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate.CreateTemplateFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PickSms : BaseFragment<PickSmsPresenter>(),PickSmsView {
    override val layoutId = R.layout.fragment_pick_sms
    override val presenter = PickSmsPresenter(this)
    override val TAG = "PickSMSFragment"

    lateinit var rvSms: RecyclerView
    lateinit var adapter: SmsAdapter

    override fun initializeUI(view: View) {
        rvSms = view.findViewById(R.id.rv_sms_list)
        adapter = SmsAdapter(presenter)
        rvSms.adapter = adapter
        rvSms.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun loadSms() {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.notifyDataSetChanged()
        }
    }

    override fun pickSms(index: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            baseActivity?.replaceFragment(this@PickSms,CreateTemplateFragment("$index"))
//            baseActivity?.replaceFragment(CreateTemplateFragment("$index"))
        }
    }

}