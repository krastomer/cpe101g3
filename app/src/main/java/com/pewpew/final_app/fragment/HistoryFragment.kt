package com.pewpew.final_app.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exploration.cpe101.CustomAdapter
import com.exploration.cpe101.logs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pewpew.final_app.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

import com.pewpew.final_app.R
import com.pewpew.final_app.User
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_history.*
import java.time.LocalDate
import java.time.LocalTime


class HistoryFragment : Fragment() {

    var phoneNumber:String = "0864062057"
    val user = FirebaseAuth.getInstance().currentUser
    var currentdata:Int =0
    var uid:String = "5403ad4f-ced2-4845-8211-ccd6226cbf24"
    var boardid:String =""

    fun getNumberThenPhone(){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users/users-"+uid)
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                phoneNumber = p0.child("phone").value.toString()
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:"+phoneNumber)
                startActivity(intent)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("MainActivity","Get Number ERROR!")
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_history, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        user?.let {
            uid = user.uid
        }

        val users = ArrayList<User>()
//        Log.e("MainActivity","${database}")
        val database2 = FirebaseDatabase.getInstance()
        val myRef2 = database2.getReference("users/users-"+uid)

        myRef2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                boardid= p0.child("boardid").value.toString()
                Log.e("MainActivity",boardid)
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.w("MainActivity","Error")
            }
        })

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("logs")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                val value = p0!!.getValue(String::class.java)
          //      val value = p0?.value
                var i:Int=0
                for(h in p0.children){
                    val day:String = h.child("time").child("day").value.toString()
                    val month:String = h.child("time").child("month").value.toString()
                    val year:String = h.child("time").child("year").value.toString()
                    val hour:String = h.child("time").child("hour").value.toString()
                    val minute:String = h.child("time").child("minute").value.toString()
                    val time:String = day+"-"+month+"-"+year+" "+hour+":"+minute
                    Log.w("MainActivity", h.toString())
                    Log.w("MainActivity", h.child("uid").value.toString())
                    if(i>=currentdata) {
                        if(h.child("uid").value.toString()==boardid) {

                            if(h.child("time").child("day").value.toString()== LocalDate.now().dayOfMonth.toString()){
                                if(h.child("time").child("hour").value.toString().toDouble()>= LocalTime.now().hour.toString().toDouble()-2){
                                    Log.w("MainActivity","Found Unusual Activity")
                                    getNumberThenPhone()
                                }
                            }

                            users.add(User(h.child("level").value.toString().toDouble(), time))
                            val adapter = CustomAdapter(users)
                            list_recycler_view.setLayoutManager(LinearLayoutManager(activity));
                            list_recycler_view.adapter = adapter
                        }
                        currentdata = currentdata +1
                    }
                    i = i+1
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("MainActivity","Error")
            }
        })

        val adapter = CustomAdapter(users)

        //now adding the adapter to recyclerview

        list_recycler_view.setLayoutManager(LinearLayoutManager(activity));
        list_recycler_view.adapter = adapter
    }

    companion object {
        fun newInstance(): HistoryFragment = HistoryFragment()
    }


}
