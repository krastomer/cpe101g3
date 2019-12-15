package com.pewpew.final_app.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.exploration.cpe101.CustomAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.pewpew.final_app.R
import com.pewpew.final_app.RegisterActivity
import com.pewpew.final_app.User
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_overall.*
import java.util.ArrayList
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import androidx.core.view.get
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet



class OverallFragment : Fragment() {

    private lateinit var dataValue: Entry
    private lateinit var lineDataSet: LineDataSet
    private lateinit var dataSet: ILineDataSet
    private lateinit var data2: LineData

    val user = FirebaseAuth.getInstance().currentUser
    var uid: String = "5403ad4f-ced2-4845-8211-ccd6226cbf24"
    var boardid: String = ""

    var totallogs: Int = 0
    var totalmonth: Int = 0
    var month1: Int = 0
    var month2: Int = 0
    var month3: Int = 0
    var totalyear: Int = 0
    var year1: Int = 0
    var year2: Int = 0
    var year3: Int = 0

    var phoneNumber: String = "0864062057"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        user?.let {
            uid = user.uid
        }
        ///Check If Exist

        val rootView: View = inflater.inflate(R.layout.fragment_overall, container, false)

        val database2 = FirebaseDatabase.getInstance()
        val myRef2 = database2.getReference("users/users-" + uid)

        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                boardid = p0.child("boardid").value.toString()
//                Log.e("MainActivity",boardid)
            }

            override fun onCancelled(p0: DatabaseError) {
//                Log.w("MainActivity","Error")
            }
        })


        val time = LocalDate.now()
        val year = time.year.toString().toDouble()
        val month = time.monthValue.toString().toDouble()
        val day = time.dayOfMonth.toString().toDouble()
//        Log.e("MainActivity",year.toString())


        return inflater.inflate(R.layout.fragment_overall, container, false)
    }

    fun getNumberThenPhone() {
        if(activity==null)return
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users/users-" + uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                phoneNumber = p0.child("phone").value.toString()
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phoneNumber)
                startActivity(intent)
            }

            override fun onCancelled(p0: DatabaseError) {
//                Log.e("MainActivity","Get Number ERROR!")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        val database5 = FirebaseDatabase.getInstance()
        val myRef5 = database5.getReference("users")
        var exist: Int = 0
        myRef5.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                exist = 0
                for (h in p0.children) {
                    if (h.key.toString() == "users-" + uid) {
                        exist = 1
//                        Log.w("MainActivity",h.key.toString())
//                        Log.e("MainActivity",h.key.toString())
                    }
//                    Log.e("MainActivity", h.key.toString())
                }
                if (exist == 0) {
//                    Log.e("RegisterActivity","Calling Register")
                    val intent: Intent = Intent(activity, RegisterActivity::class.java)
                    startActivity(intent)
                    return
                }
            }

            override fun onCancelled(p0: DatabaseError) {
//                Log.w("MainActivity","Error")
            }
        })

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("logs")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var i: Int = 0
//                Log.d("MainActivity",boardid)
                for (h in p0.children) {
                    if (h.child("uid").value.toString() == boardid) {
                        if (h.child("time").child("day").value.toString() == LocalDate.now().dayOfMonth.toString()) {
                            if (h.child("time").child("hour").value.toString().toDouble() >= LocalTime.now().hour.toString().toDouble() - 2) {
//                                Log.w("MainActivity","Found Unusual Activity")
                                if(h.child("level").value.toString().toDouble()>50) {
                                    getNumberThenPhone()
                                }
                            }
                        }

                        if (h.child("level").value.toString().toInt() < 30) {
                            month1++
                        } else if (h.child("level").value.toString().toInt() < 50) {
                            month2++
                        } else {
                            month3++
                        }
                        totalmonth++
//                        }
                        ///Check Yearly
//                        if(((month*12)+day)-days<365){
                        if (h.child("level").value.toString().toInt() < 30) {
                            year1++
                        } else if (h.child("level").value.toString().toInt() < 50) {
                            year2++
                        } else {
                            year3++
                        }
                        totalyear++
//                        }
                        i = i + 1
                    }
                }
                view.findViewById<TextView>(R.id.totalmonth).text =
                    "Monthly Static :   Total " + totalmonth.toString()
                view.findViewById<TextView>(R.id.totalyear).text =
                    "Yearly Static :   Total " + totalyear.toString()
                view.findViewById<TextView>(R.id.month1).text = month1.toString()
                view.findViewById<TextView>(R.id.month2).text = month2.toString()
                view.findViewById<TextView>(R.id.month3).text = month3.toString()
                view.findViewById<TextView>(R.id.year1).text = year1.toString()
                view.findViewById<TextView>(R.id.year2).text = year2.toString()
                view.findViewById<TextView>(R.id.year3).text = year3.toString()


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
        view.findViewById<TextView>(R.id.totalmonth).text =
            "Monthly Static :   Total " + totalmonth.toString()
        view.findViewById<TextView>(R.id.totalyear).text =
            "Yearly Static :   Total " + totalyear.toString()
        view.findViewById<TextView>(R.id.month1).text = month1.toString()
        view.findViewById<TextView>(R.id.month2).text = month2.toString()
        view.findViewById<TextView>(R.id.month3).text = month3.toString()
        view.findViewById<TextView>(R.id.year1).text = year1.toString()
        view.findViewById<TextView>(R.id.year2).text = year2.toString()
        view.findViewById<TextView>(R.id.year3).text = year3.toString()

    }
}
//
///*
//    fun createGraph(){
//        val graph1 = floatArrayOf(113000f, 183000f, 188000f, 695000f, 324000f, 230000f, 188000f, 15000f, 126000f, 5000f, 33000f)
//        val legendArr = arrayOf("05/21", "05/22", "05/23", "05/24", "05/25", "05/26", "05/27", "05/28", "05/29", "05/30", "05/31")
//
//   //     lineDataSet.addEntry(dataValue)
//
//     //   dataSet.addEntry(dataValue)
//
//
//    //    data2.addDataSet(dataSet)
//     //   graph.data = data2
//        graph.invalidate()
//        return view
//    }
//
//
//}
