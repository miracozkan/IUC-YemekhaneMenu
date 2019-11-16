package com.miracozkan.yemekhanemenu.reciever

import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.10.2019 - 11:13          │
//└─────────────────────────────┘

class FoodImageReceiver : BroadcastReceiver() {

    private var txtState: TextView? = null

    override fun onReceive(p0: Context?, p1: Intent?) {

        val bundle = p1?.extras
        val resultCode = bundle?.getInt(RESULT_PARAM)
        bundle?.let {
            if (resultCode == RESULT_OK) {
                Toast.makeText(p0, "Download Finish", Toast.LENGTH_SHORT).show()
                // Get texts from resource file. If you don't have context in the class,
                // you can pass resource id to TextViews like txtState.setText(R.string.download_done)
                txtState?.text = "Download Done!!!"
                Log.e("FoodImageReciever", "if -> Something WentWrongg")

            } else {
                Toast.makeText(p0, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
                Log.e("FoodImageReciever", "else -> Something WentWrongg")
            }
        }
    }

    companion object {
        const val RESULT_PARAM = "RESULT"
    }
}