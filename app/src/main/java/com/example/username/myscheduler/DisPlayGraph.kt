package com.example.username.myscheduler

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_dis_play_graph.*
import kotlinx.android.synthetic.main.activity_main.*

class DisPlayGraph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dis_play_graph)

        //actionbar setting
        setTitle("")
        setSupportActionBar(graph_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sampleData: List<Int> = listOf(10,20,20,30)
        val sampleLabel: List<String> = listOf("緑", "青", "赤", "灰")

        val pieEntryList: List<PieEntry> = sampleData.zip(sampleLabel).map {
            PieEntry(it.first.toFloat(), it.second)
        }
        val pieDataSet = PieDataSet(pieEntryList, "ラベル")
        pieDataSet.colors = listOf(Color.GREEN, Color.BLUE, Color.RED, Color.GRAY)
        val pieData = PieData(pieDataSet)

        my_piechart.data = pieData
    }
}