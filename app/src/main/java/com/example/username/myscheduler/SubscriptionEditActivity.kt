package com.example.username.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class SubscriptionEditActivity : AppCompatActivity() {
    //RealmのLateInit
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)
        realm = Realm.getDefaultInstance()

        //cycleに代入する変数
        var cycle: String = ""

        val subscriptionId = intent?.getLongExtra("subscription_id", -1L)
        if(subscriptionId != -1L) {
            val subscription = realm.where<Subscription>().equalTo("id", subscriptionId).findFirst()
            serviceNameEdit.setText(subscription?.serviceName)
            moneyEdit.setText(subscription?.money.toString())
        }

        cycleEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if(it.isNotEmpty()) cycle = it
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        save.setOnClickListener {
            when(subscriptionId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Subscription>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val subscription = realm.createObject<Subscription>(nextId)
                        subscription.serviceName = serviceNameEdit.text.toString()
                        subscription.money = moneyEdit.text.toString().toInt()
                        subscription.cycle = cycle
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val subscription = realm.where<Subscription>().equalTo("id", subscriptionId).findFirst()
                        subscription?.serviceName = serviceNameEdit.text.toString()
                        subscription?.money = moneyEdit.text.toString().toInt()
                        subscription?.cycle = cycle
                    }
                    alert("変更しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        delete.setOnClickListener{
            realm.executeTransaction {
                realm.where<Subscription>().equalTo("id", subscriptionId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
