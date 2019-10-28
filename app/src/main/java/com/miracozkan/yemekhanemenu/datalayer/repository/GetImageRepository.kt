package com.miracozkan.yemekhanemenu.datalayer.repository

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.miracozkan.yemekhanemenu.BuildConfig
import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.db.FoodImageDao
import com.miracozkan.yemekhanemenu.datalayer.model.FoodImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 20:55          │
//└─────────────────────────────┘

class GetImageRepository(
    private val text: List<String>,
    private val foodImageDao: FoodImageDao
) : BaseRepository() {

    val links = ArrayList<String>()

    init {
        getImageDB()
    }

    fun getLink() {
        GlobalScope.launch {
            text.forEach { _name ->
                val link = "${BuildConfig.GOOGLE_URL}$_name&source=lnms&tbm=isch"
                val doc = Jsoup.connect(link)
                    .get()
                    .select("div.rg_meta")
                val jsonObject =
                    JsonParser().parse(doc.first().childNode(0).toString()) as JsonObject
                foodImageDao.insertFoodImage(
                    FoodImage(
                        name = _name,
                        link = jsonObject.get("ou").toString()
                    )
                )
            }
        }
    }

    private fun getImageDB() {
        GlobalScope.launch {
            text.forEach {
                val value = foodImageDao.getImageFood(it)
                links.add(value.link)
                Log.e("From DB", value.link + "<----->" + value.name)
            }
        }
    }

}