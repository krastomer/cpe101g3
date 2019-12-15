package com.pewpew.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser
    var userid:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        user?.let {
            userid = user.uid
            Log.e("MainActivity",userid)
        }

        submit.setOnClickListener{
            registeruser()
        }

    }

    fun registeruser(){
        var nametext:EditText = findViewById(R.id.nametxt)
        var phonetext:EditText = findViewById(R.id.phonetxt)
        var boardtex:EditText = findViewById(R.id.boardid)
        val name:String = nametext.text.toString()
        val phone:String = phonetext.text.toString()
        val board:String = boardtex.text.toString()

        val database2 = FirebaseDatabase.getInstance()
        val ref = database2.getReference("users")
        ref.child("users-"+userid).child("name").setValue(name)
        ref.child("users-"+userid).child("phone").setValue(phone)
        ref.child("users-"+userid).child("boardid").setValue(board)
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}
