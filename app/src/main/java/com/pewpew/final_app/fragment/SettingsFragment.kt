package com.pewpew.final_app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.pewpew.final_app.LoginActivity
import com.pewpew.final_app.MainActivity

import com.pewpew.final_app.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_signOut.setOnClickListener {
            Toast.makeText(context,"Btn Press",Toast.LENGTH_SHORT)
        }

    }

  /*  private fun signOutBtn(){
        auth.signOut()
        val intent = Intent(activity,LoginActivity::class.java)
        googleSignInClient.revokeAccess().addOnCompleteListener
        googleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
        }
    }*/
}
