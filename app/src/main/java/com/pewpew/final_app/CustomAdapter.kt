package com.exploration.cpe101

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.pewpew.final_app.R
import com.pewpew.final_app.User
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * Created by Belal on 6/19/2017.
 */

class CustomAdapter(val userList: ArrayList<User>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: User) {
            val layout = itemView.findViewById(R.id.linearlayout) as LinearLayout
            val textViewName = itemView.findViewById(R.id.textViewname) as TextView
            val textViewAddress  = itemView.findViewById(R.id.textViewdetail) as TextView
            textViewName.text = "Alcohol Level : " + user.level.toString() + " mg%"
            if(user.level > 50){
                textViewName.setTextColor(Color.parseColor("#FFFFFFFF"))
                layout.setBackgroundColor(Color.parseColor("#CF6679"));
            }
            else if(user.level > 30){
                textViewName.setTextColor(Color.parseColor("#ADC200"))
                layout.setBackgroundColor(Color.parseColor("#1E1E1E"));
            }
            else {
                textViewName.setTextColor(Color.parseColor("#FFFFFFFF"))
                layout.setBackgroundColor(Color.parseColor("#1E1E1E"));
            }
            textViewAddress.text = user.detail
        }
    }
}