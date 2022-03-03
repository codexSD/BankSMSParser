package com.codexSoftSD.bankCardsTemplateManager.ui.templates

import android.annotation.SuppressLint
import android.content.Context
import android.system.Os.accept
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.templates.createTemplate.CreateTemplateFragment
import com.codexSoftSD.bankCardsTemplateManager.ui.templates.pickSms.PickSms
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TemplateFragment : BaseFragment<TemplatePresenter>(),TemplateView {
    override val layoutId = R.layout.fragment_template
    override val presenter: TemplatePresenter = TemplatePresenter(this)
    override val TAG = "Template Fragment"
    lateinit var tvTemplateCount:TextView;
    lateinit var rvTemplates: RecyclerView
    private lateinit var templatesAdapter: TemplatesAdapter
    lateinit var addNewTemplateButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        presenter = TemplatePresenter(this)
    }
    override fun initializeUI(view: View) {
        tvTemplateCount = view.findViewById(R.id.tv_template_count)
        rvTemplates = view.findViewById(R.id.rv_templates)
        templatesAdapter = TemplatesAdapter(presenter)
        rvTemplates.layoutManager = LinearLayoutManager(requireContext())
        rvTemplates.adapter = templatesAdapter
        addNewTemplateButton = view.findViewById(R.id.btn_new_template)
        addNewTemplateButton.setOnClickListener { presenter.createNewTemplate() }
    }

    @SuppressLint("SetTextI18n")
    override fun setTemplateCount() {
        CoroutineScope(Dispatchers.Main).launch {
            tvTemplateCount.text = getString(R.string.template_count)+": "+presenter.templateCount()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun refreshTemplates() {
        CoroutineScope(Dispatchers.Main).launch{
            tvTemplateCount.text = getString(R.string.template_count)+": "+presenter.templateCount()
            templatesAdapter.notifyDataSetChanged()
        }
    }

    override fun loadTemplate(templateId: Int) {
//        baseActivity?.loadFragment(CreateTemplateFragment("2"))
        baseActivity?.loadFragment(CreateTemplateFragment(templateId))
    }

    override fun createTemplate() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.import_sms))
            .setMessage(resources.getString(R.string.import_sms_info))
            .setNegativeButton(resources.getString(R.string.new_a)) { dialog, which ->
                baseActivity?.loadFragment(CreateTemplateFragment(-1))
            }
            .setPositiveButton(resources.getString(R.string.import_a)) { dialog, which ->
                baseActivity?.loadFragment(PickSms())
            }
            .show()
    }
}