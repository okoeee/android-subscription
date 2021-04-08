package com.example.username.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)
        realm = Realm.getDefaultInstance()

        val subscriptionId = intent?.getLongExtra("subscription_id", -1L)
        if(subscriptionId != -1L) {
            val subscription = realm.where<Subscription>().equalTo("id", subscriptionId).findFirst()
            serviceNameEdit.setText(subscription?.serviceName)
            moneyEdit.setText(subscription?.money)
        }

        save.setOnClickListener {
            when(subscriptionId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Subscription>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val subscription = realm.createObject<Subscription>(nextId)
                        subscription.serviceName = serviceNameEdit.text.toString()
                        subscription.money = moneyEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val subscription = realm.where<Subscription>().equalTo("id", subscriptionId).findFirst()
                        subscription?.serviceName = serviceNameEdit.text.toString()
                        subscription?.money = moneyEdit.text.toString()
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
