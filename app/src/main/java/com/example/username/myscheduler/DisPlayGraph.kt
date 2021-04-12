package com.example.username.myscheduler

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_dis_play_graph.*
import kotlinx.android.synthetic.main.activity_main.*

class DisPlayGraph : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dis_play_graph)

        //ツールバーのセッティング
        setTitle("")
        setSupportActionBar(graph_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //データの取得
        realm = Realm.getDefaultInstance()
        val subscription = realm.where<Subscription>().findAll()
        println(subscription)

        //合計金額
        val sum = subscription.sum("money").toLong().toString()

        //realmからlistに追加する処理
        val sampleData = mutableListOf<Int>()
        val sampleLabel = mutableListOf<String>()
        subscription.forEach{obj ->
            sampleData.add((obj.money.toFloat() / sum.toFloat() * 100).toInt())
        }
        subscription.forEach{obj ->
            sampleLabel.add(obj.serviceName)
        }

        //pie_chartのセッティング
        //データがない時の文言
        my_piechart.setNoDataText("データを追加してください")
        //アニメーション
        my_piechart.animateY(1000, Easing.EaseInOutCubic)
        //センターテキスト
        my_piechart.setCenterText("合計金額\n" + "¥" + sum)
        my_piechart.setCenterTextSize(24F)
        //凡例
        my_piechart.legend.setTextSize(18F)
        //グラフの説明文
        val desc = Description()
        desc.text = ""
        my_piechart.description = desc


        val pieEntryList: List<PieEntry> = sampleData.zip(sampleLabel).map {
            PieEntry(it.first.toFloat(), it.second)
        }
        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.colors = listOf(Color.GREEN, Color.BLUE, Color.RED, Color.GRAY)
        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(20F)

        my_piechart.data = pieData
    }
}