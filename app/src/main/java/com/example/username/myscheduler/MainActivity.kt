package com.example.username.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        realm = Realm.getDefaultInstance()

        val subscription = realm.where<Subscription>().findAll()
        listView.adapter = SubscriptionAdapter(this,  subscription)

        fab.setOnClickListener { view ->
            startActivity<ScheduleEditActivity>()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val subscription = parent.getItemAtPosition(position) as Subscription
            startActivity<ScheduleEditActivity>("subscription_id" to subscription.id )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
