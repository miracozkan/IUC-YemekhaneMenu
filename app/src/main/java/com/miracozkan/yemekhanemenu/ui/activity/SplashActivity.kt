package com.miracozkan.yemekhanemenu.ui.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.remote.RetrofitClient
import com.miracozkan.yemekhanemenu.util.*
import com.miracozkan.yemekhanemenu.vm.NetworkCallViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val DATE_FORMAT_2 = "dd.MM.yyyy"

    private val edittedCurrentDate by lazy { getCurrentDate().replace(".", "").substring(2) }

    private val networkCallRepository by lazy {
        DependencyUtil.getNetworkCallRepository(
            edittedCurrentDate.toInt(),
            RetrofitClient.getClient(),
            ProjectDatabase.getInstance(this).projectDao()
        )
    }

    private val networkCallViewModel by lazy {
        ViewModelProviders.of(
            this,
            ViewModelFactory(networkCallRepository)
        ).get(NetworkCallViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_title)
        )

        val currentDate = getCurrentDate()
        val edittedCurrentDate = currentDate.replace(".", "").substring(2)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("date", currentDate)

        networkCallViewModel.lastUpdate.observe(this, androidx.lifecycle.Observer {
            if (edittedCurrentDate.toInt() == it) {
                prgSplash.hide()
                startActivity(intent)
            } else {
                if (checkConnection()) {
                    runObserve(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Güncel Liste İcin İnternet Baglantınızı Acınız",
                        Toast.LENGTH_SHORT
                    ).show()
                    prgSplash.hide()
                    startActivity(intent)
                }
            }
        })
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Test"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    private fun runObserve(intent: Intent) {
        networkCallViewModel.resultReq.observe(this, androidx.lifecycle.Observer { _response ->
            when (_response.status) {
                Status.LOADING -> {
                    prgSplash.show()
                }
                Status.SUCCESS -> {
                    prgSplash.hide()
                    startActivity(intent)
                }
                Status.ERROR -> {
                    prgSplash.hide()
                    Toast.makeText(this, _response.message, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
        })
    }

    private fun checkConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_2)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }
}
