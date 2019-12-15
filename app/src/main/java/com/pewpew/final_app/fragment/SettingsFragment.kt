package com.pewpew.final_app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pewpew.final_app.LoginActivity
import com.pewpew.final_app.MainActivity
import com.pewpew.final_app.R
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.android.synthetic.main.fragment_settings.*


private lateinit var auth: FirebaseAuth

private lateinit var googleSignInClient: GoogleSignInClient

class SettingsFragment : Fragment() {
    var uid = "12344"
    var boardid:String = "12344"
    var phoneNumber:String="0864062057"
    val user = FirebaseAuth.getInstance().currentUser

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        val database1 = FirebaseDatabase.getInstance()
        val myRef1 = database1.getReference("logs")
        myRef1.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var i: Int = 0
                for (h in p0.children) {
                    if(p0.child("uid").value.toString()==boardid) {
                        if (h.child("time").child("day").value.toString() == LocalDate.now().dayOfMonth.toString()) {
                            if (h.child("time").child("hour").value.toString().toDouble() >= LocalTime.now().hour.toString().toDouble() - 2) {
                                Log.w("MainActivity", "Found Unusual Activity")
                                getNumberThenPhone()
                            }
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("MainActivity","Error!!!!")
            }
        })

        val rootView: View = inflater.inflate(R.layout.fragment_settings, container, false)
        ///Get Default Value

        user?.let {
            uid = user.uid
        }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users/users-" + uid)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                val value = p0!!.getValue(String::class.java
                var phone:String = p0.child("phone").value.toString()
                var device:String = p0.child("boardid").value.toString()
                var name:String = p0.child("name").value.toString()
                rootView.findViewById<EditText>(R.id.phonetxt).setText(phone)
                rootView.findViewById<EditText>(R.id.devicetxt).setText(device)
                var username = rootView.findViewById<View>(R.id.username) as TextView
                username.text = name
            }


            override fun onCancelled(p0: DatabaseError) {
                Log.w("MainActivity", "Error")
            }
        })
        ///
        val phoneupdate: Button = rootView.findViewById<View>(R.id.phoneupdate) as Button
        phoneupdate.setOnClickListener {
            var newphone = rootView.findViewById<EditText>(R.id.phonetxt).text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users").child("users-" + uid).child("phone")
            myRef.setValue(newphone)
            Log.d("MainActivity", "Update Phone Success")


        }

        val deviceupdate: Button = rootView.findViewById<View>(R.id.deviceupdate) as Button
        deviceupdate.setOnClickListener {
            var newdevice = rootView.findViewById<EditText>(R.id.devicetxt).text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users").child("users-" + uid).child("boardid")
            myRef.setValue(newdevice)
            Log.d("MainActivity", "Update Phone Success")


        }


        val signOutButton : Button = rootView.findViewById<View>(R.id.btn_signout) as Button
        signOutButton.setOnClickListener{
            signOutBtn()
        }


        return rootView
    }

    private fun signOutBtn(){
        auth = FirebaseAuth.getInstance()
        auth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)

        googleSignInClient.signOut().addOnCompleteListener {
            var intent = Intent(this@SettingsFragment.context,LoginActivity::class.java)
            startActivity(intent)

        }
    }
}
