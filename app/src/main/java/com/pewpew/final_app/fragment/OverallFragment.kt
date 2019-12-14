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

import com.pewpew.final_app.R
import kotlinx.android.synthetic.main.fragment_overall.*
import java.util.ArrayList

class OverallFragment : Fragment() {

  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chartView.lineColor = Color.BLACK
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overall, container, false)
    }

/*
    fun createGraph(){
        val graph1 = floatArrayOf(113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f)
        val legendArr = arrayOf("05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31")

        val entity = ChartEntity(Color.WHITE,graph1)

        val list = ArrayList<ChartEntity>()
        list.addAll(list)
        Log.e("OverallFragment",list.toString())
     //   chartView.setLegend(legendArr)
      //  chartView.setList(list)
    }*/

}

