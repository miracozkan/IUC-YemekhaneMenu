package com.miracozkan.yemekhanemenu.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
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

    private val networkCallRepository by lazy {
        DependencyUtil.getNetworkCallRepository(
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

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("date", getCurrentDate())

        if (checkConnection()) {
            runObserve(intent)
        } else {
            Toast.makeText(this, "Internet Baglantınız Kapalı", Toast.LENGTH_SHORT).show()
            prgSplash.hide()
            startActivity(intent)
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
