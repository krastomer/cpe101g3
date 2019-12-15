package com.pewpew.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pewpew.final_app.fragment.HistoryFragment
import com.pewpew.final_app.fragment.OverallFragment
import com.pewpew.final_app.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser
    var userid:String = ""
    private val mOnNavigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.btn_overall ->{
                replaceFragment(OverallFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.btn_history ->{
                replaceFragment(HistoryFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.btn_settings ->{
                replaceFragment(SettingsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(OverallFragment())


        ///
        bottom_nav_down.setOnNavigationItemSelectedListener(mOnNavigation)
        user?.let {
            userid = user.uid
//            Log.e("MainActivity",userid)
        }
        ///Check If Exist
        val database2 = FirebaseDatabase.getInstance()
        val myRef2 = database2.getReference("users")
        var exist:Int = 0
        myRef2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                exist=0
                for (h in p0.children) {
                    if (h.key.toString() == "users-"+userid) {
                        exist = 1
//                        Log.w("MainActivity",h.key.toString())
//                        Log.e("MainActivity",h.key.toString())
                    }
//                    Log.e("MainActivity", h.key.toString())
                }
                if(exist==0){
//                    val intent:Intent = Intent(this@MainActivity, RegisterActivity::class.java)
//                    startActivity(intent)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
//                Log.w("MainActivity","Error")
            }
        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment)
        fragmentTransaction.commit()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
