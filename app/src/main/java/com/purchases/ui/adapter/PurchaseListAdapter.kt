package com.purchases.ui.adapter

import android.support.v7.widget.*
import android.view.*
import android.widget.*
import android.widget.PopupMenu
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.ui.activity.*
import io.realm.*


class PurchaseListAdapter(private val activity: PurchaseListActivity, data: OrderedRealmCollection<PurchaseList>) : RealmRecyclerViewAdapter<PurchaseList, PurchaseListAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchases, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data!![position].name
        holder.purchaseList = data!![position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item!!.itemId) {
                R.id.menuBuy -> {
                    activity.buyPurchases(purchaseList)
                }
                R.id.menuFavorite -> {
                    activity.addToFavorite(purchaseList)
                }
                R.id.menyDelete -> {
                    activity.presenter.deletePurchases(activity.realm, purchaseList)
                }
            }
            return false
        }

        var name: TextView = view.findViewById(R.id.textView)
        lateinit var purchaseList: PurchaseList

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
            val popup = PopupMenu(v.context, v)
            popup.inflate(R.menu.menu_purchases)
            popup.setOnMenuItemClickListener(this)
            popup.show()

            return true
        }

        override fun onClick(view: View) {
            activity.editPurchases(purchaseList)
        }
    }
}