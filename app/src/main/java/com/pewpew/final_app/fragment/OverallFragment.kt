package com.pewpew.final_app.fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ir.farshid_roohi.linegraph.ChartEntity

import com.pewpew.final_app.R
import ir.farshid_roohi.linegraph.LineChart
import kotlinx.android.synthetic.main.fragment_overall.view.*

class OverallFragment : Fragment() {

    //   private var lineChart : LineChart = findViewById(R.id.lineChart)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var MainrootView: View = inflater.inflate(R.layout.fragment_overall, container, false)

        val entity = ChartEntity(Color.WHITE,graph1)

        var lineChart = MainrootView.findViewById<LineChart>(R.id.lineChart)

        val list = ArrayList<ChartEntity>()
        list.add(entity)
        lineChart.setLegend(legendArr)
        lineChart.setList(list)
        Log.e("MainActivity",lineChart.toString())
        return inflater.inflate(R.layout.fragment_overall, container, false)
    }

    private val graph1 = floatArrayOf(1f, 1f, 180f, 1f, 324000f, 0f, 180f, 15000f, 1200f, 5000f, 33000f)
    private val legendArr = arrayListOf("05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31")


}
