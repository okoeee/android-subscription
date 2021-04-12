package com.example.username.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

        //Toolbar defined in the layout
        realm = Realm.getDefaultInstance()

        //realmとListViewの紐付け
        val subscription = realm.where<Subscription>().findAll()
        listView.adapter = SubscriptionAdapter(this,  subscription)

        fab.setOnClickListener { view ->
            startActivity<SubscriptionEditActivity>()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val subscription = parent.getItemAtPosition(position) as Subscription
            startActivity<SubscriptionEditActivity>("subscription_id" to subscription.id )
        }

        //タイトルのセット
        setTitle("submane")
    }

    //オプションメニューの追加
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_graph -> {
            // User chose the "Settings" item, show the app settings UI...
            startActivity<DisPlayGraph>()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}


