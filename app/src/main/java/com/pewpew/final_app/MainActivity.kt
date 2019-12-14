package com.pewpew.final_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pewpew.final_app.fragment.HistoryFragment
import com.pewpew.final_app.fragment.OverallFragment
import com.pewpew.final_app.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.btn_overall ->{
                replaceFragment(OverallFragment())
                return@OnNavigationItemSelectedListener true
                println("overall")
            }
            R.id.btn_history ->{
                println("history")
                replaceFragment(HistoryFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.btn_settings ->{
                println("settings")
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
        bottom_nav_down.setOnNavigationItemSelectedListener(mOnNavigation)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment)
        fragmentTransaction.commit()
    }

}
