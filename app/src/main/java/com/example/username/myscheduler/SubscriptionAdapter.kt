package com.example.username.myscheduler

import android.content.Context
import android.media.CamcorderProfile.get
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import io.realm.RealmResults

class SubscriptionAdapter(context: Context, data: OrderedRealmCollection<Subscription>?) : RealmBaseAdapter<Subscription>(data) {

    inner class ViewHolder(cell: View) {
        val serviceName = cell.findViewById<TextView>(R.id.item1)
        val money = cell.findViewById<TextView>(R.id.item2)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val viewHolder: ViewHolder

        when(convertView) {
            null -> {
                //カスタムレイアウト
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(R.layout.list_item_layout, parent, false)

                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            }
            else -> {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
        }
        adapterData?.run {
            val subscription = get(position)
            viewHolder.serviceName.text = if(subscription.serviceName != null) {subscription.serviceName} else {"serviceNameがぬる"}
            viewHolder.money.text = if(subscription.money != null) {subscription.money.toString()} else {"moneyがぬる"}
        }
        return view
    }
}