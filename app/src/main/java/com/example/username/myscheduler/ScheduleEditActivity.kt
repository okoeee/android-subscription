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

//        val scheduleId = intent?.getLongExtra("schedule_id", -1L)
//        if (scheduleId != -1L) {
//            val schedule = realm.where<Schedule>()
//                    .equalTo("id", scheduleId).findFirst()
//            serviceNameEdit.setText(
//                    DateFormat.format("yyyy/MM/dd", schedule?.date))
//            titleEdit.setText(schedule?.title)
//            detailEdit.setText(schedule?.detail)
//            delete.visibility = View.VISIBLE
//        } else {
//            delete.visibility = View.INVISIBLE
//        }

        save.setOnClickListener {
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

//            when (scheduleId) {
//                -1L -> {
//                    realm.executeTransaction {
//                        val maxId = realm.where<Schedule>().max("id")
//                        val nextId = (maxId?.toLong() ?: 0L) + 1L
//                        val schedule = realm.createObject<Schedule>(nextId)
//                        serviceNameEdit.text.toString().toDate("yyyy/MM/dd")?.let {
//                            schedule.date = it
//                        }
//                        schedule.title = titleEdit.text.toString()
//                        schedule.detail = detailEdit.text.toString()
//                    }
//                    alert("追加しました") {
//                        yesButton { finish() }
//                    }.show()
//                }
//                else -> {
//                    realm.executeTransaction {
//                        val schedule = realm.where<Schedule>()
//                                .equalTo("id", scheduleId).findFirst()
//                        serviceNameEdit.text.toString().toDate("yyyy/MM/dd")?.let {
//                            schedule?.date = it
//                        }
//                        schedule?.title = titleEdit.text.toString()
//                        schedule?.detail = detailEdit.text.toString()
//                    }
//                    alert("修正しました") {
//                        yesButton { finish() }
//                    }.show()
//                }
//            }
        }

//        delete.setOnClickListener{
//            realm.executeTransaction {
//                realm.where<Schedule>().equalTo("id", scheduleId)?.findFirst()?.deleteFromRealm()
//            }
//            alert("削除しました") {
//                yesButton { finish() }
//            }.show()
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
