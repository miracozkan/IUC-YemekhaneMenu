package com.miracozkan.yemekhanemenu.service

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.miracozkan.yemekhanemenu.BuildConfig
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.model.FoodImage
import com.miracozkan.yemekhanemenu.reciever.FoodImageReceiver.Companion.RESULT_PARAM
import org.jsoup.Jsoup

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 17.10.2019 - 10:52          │
//└─────────────────────────────┘

class FoodImageService : IntentService("GoogleParse") {

    private val foodImageDao by lazy { ProjectDatabase.getInstance(this).foodImage() }
    private var result = Activity.RESULT_CANCELED
    private lateinit var foodList: ArrayList<String>

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            foodList = it.getStringArrayListExtra("foodList")!!
        }
        return super.onStartCommand(intent, flags, startId)
    }

    //TODO Service's have to start fragment because of coming list or list has to parse while runnig splash activity

    override fun onHandleIntent(p0: Intent?) {
        var link: String
        try {
            foodList.forEach { _foodName ->
                link = "${BuildConfig.GOOGLE_URL}$_foodName&source=lnms&tbm=isch"
                val doc = Jsoup.connect(link)
                    .get()
                    .select("div.rg_meta")
                val jsonObject =
                    JsonParser().parse(doc.first().childNode(0).toString()) as JsonObject
                foodImageDao.insertFoodImage(
                    FoodImage(
                        name = _foodName,
                        link = jsonObject.get("ou").toString()
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("GoogleParse Error -> ", e.toString())
            e.printStackTrace()
        }

    }

    private fun publishResult(result: Int) {
        // I haven't use this logic before but this name should be constant and there should be
        // better way to do this. Should do some research about it. I'd be appreciated if you let me know
        // when you find a better way.
        val intent = Intent("com.miracozkan.yemekhanemenu.service.FoodImageService")
        intent.putExtra(RESULT_PARAM, result)
        sendBroadcast(intent)
    }
}