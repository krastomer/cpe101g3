package com.pewpew.final_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.pewpew.final_app.R


class OverallFragment : Fragment() {

    private lateinit var dataValue : Entry
    private lateinit var lineDataSet : LineDataSet
    private lateinit var dataSet : ILineDataSet
    private lateinit var data2 : LineData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_overall, container, false)

        val graph : LineChart = view.findViewById(R.id.graphView)

   //     lineDataSet.addEntry(dataValue)

     //   dataSet.addEntry(dataValue)


    //    data2.addDataSet(dataSet)
     //   graph.data = data2
        graph.invalidate()
        return view
    }


}
