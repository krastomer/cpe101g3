package com.pewpew.final_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed",e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct : GoogleSignInAccount){
        Log.d(TAG,"firebaseAuthWithGoogle:" + acct.id!!)
     //   showProgressDialog()
        val credential = GoogleAuthProvider.getCredential(acct.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext,"Not Success",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    companion object{
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}
