package com.example.username.myscheduler

import android.media.CamcorderProfile.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class SubscriptionAdapter(data: OrderedRealmCollection<Subscription>?) : RealmBaseAdapter<Subscription>(data) {

    inner class ViewHolder(cell: View) {
        val serviceName = cell.findViewById<TextView>(android.R.id.text1)
        val money = cell.findViewById<TextView>(android.R.id.text2)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val viewHolder: ViewHolder

        when(convertView) {
            null -> {
                val inflater = LayoutInflater.from(parent?.context)
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
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
            viewHolder.serviceName.text = subscription.serviceName
            viewHolder.money.text = subscription.money
        }
        return view
    }
}

