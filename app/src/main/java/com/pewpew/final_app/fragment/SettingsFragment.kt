package com.pewpew.final_app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.google.firebase.database.FirebaseDatabase
import android.view.View.OnClickListener;
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pewpew.final_app.R


class SettingsFragment : Fragment() {
    val uid = "12344"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = inflater.inflate(R.layout.fragment_settings, container, false)
        ///Get Default Value

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users/users-"+uid)

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                val value = p0!!.getValue(String::class.java
                var phone:String = p0.child("phone").value.toString()
                var device:String = p0.child("boardid").value.toString()
                rootView.findViewById<EditText>(R.id.phonetxt).setText(phone)
                rootView.findViewById<EditText>(R.id.devicetxt).setText(device)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("MainActivity","Error")
            }
        })
        ///
        val phoneupdate:Button = rootView.findViewById<View>(R.id.phoneupdate) as Button
        phoneupdate.setOnClickListener{
                var newphone = rootView.findViewById<EditText>(R.id.phonetxt).text.toString()
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("users").child("users-"+uid).child("phone")
                myRef.setValue(newphone)
                Log.d("MainActivity","Update Phone Success")


        }

        val deviceupdate:Button = rootView.findViewById<View>(R.id.deviceupdate) as Button
        deviceupdate.setOnClickListener{
            var newdevice = rootView.findViewById<EditText>(R.id.devicetxt).text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users").child("users-"+uid).child("boardid")
            myRef.setValue(newdevice)
            Log.d("MainActivity","Update Phone Success")


        }
        return rootView
    }

}
