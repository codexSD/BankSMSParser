package com.codexSoftSD.bankCardsTemplateManager.ui.purchases

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codexSoftSD.bankCardsTemplateManager.R
import com.codexSoftSD.bankCardsTemplateManager.data.purchases.Purchases
import com.codexSoftSD.bankCardsTemplateManager.ui.base.BaseFragment

class PurchasesFragment() : BaseFragment<PurchasesPresenter>(),PurchasesView {


    override val layoutId: Int = R.layout.fragment_purchases
    override lateinit var presenter: PurchasesPresenter
    override val TAG: String = "PurchasesFragment"
    private lateinit var totalPurchasesRecyclerView: RecyclerView
    private lateinit var purchasesRecyclerView: RecyclerView
    private lateinit var purchaseAdapter: PurchaseAdapter
    private lateinit var totalPurchasesAdapter: TotalPurchasesAdapter
    private lateinit var purchasesFilter: AutoCompleteTextView


    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = PurchasesPresenter(this)

    }
    override fun initializeUI(view: View) {
        totalPurchasesRecyclerView = view.findViewById(R.id.rv_total_purchases)
        totalPurchasesRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        totalPurchasesAdapter = TotalPurchasesAdapter(presenter)
        totalPurchasesRecyclerView.adapter = totalPurchasesAdapter

        purchasesRecyclerView = view.findViewById(R.id.rv_purchases)
        purchasesRecyclerView.layoutManager = LinearLayoutManager(requireContext());
        purchaseAdapter = PurchaseAdapter(presenter)
        purchasesRecyclerView.adapter = purchaseAdapter

        val items = listOf(getString(R.string.currency),getString(R.string.card),getString(R.string.account),getString(R.string.from),getString(R.string.from),)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        purchasesFilter = view.findViewById(R.id.tv_purchase_filter)
        purchasesFilter.setAdapter(adapter)
//        purchasesFilter.setOnItemClickListener { adapterView, view, i, l -> {
//
//        } }
    }

    override fun loadPurchases(purchases: List<Purchases>) {
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun notifyPurchasesChanged() {
        log("total purchases count: ${totalPurchasesAdapter.itemCount}")
//        log("purchases count: ${purchaseAdapter.itemCount}")
        totalPurchasesAdapter.notifyDataSetChanged()
        purchaseAdapter.notifyDataSetChanged()
    }

    override fun askForPurchaseDisable() {
        TODO("Not yet implemented")
    }

    override fun disablePurchase() {
        TODO("Not yet implemented")
    }


}
