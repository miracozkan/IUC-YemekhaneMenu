package com.miracozkan.yemekhanemenu.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.base.BaseActivity
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.remote.RetrofitClient
import com.miracozkan.yemekhanemenu.util.DependencyUtil
import com.miracozkan.yemekhanemenu.util.NotificationBuilder
import com.miracozkan.yemekhanemenu.util.Status.ERROR
import com.miracozkan.yemekhanemenu.util.Status.LOADING
import com.miracozkan.yemekhanemenu.util.Status.SUCCESS
import com.miracozkan.yemekhanemenu.util.Utils.Companion.DATE_FORMAT
import com.miracozkan.yemekhanemenu.util.Utils.Companion.DATE_PARAM
import com.miracozkan.yemekhanemenu.util.ViewModelFactory
import com.miracozkan.yemekhanemenu.util.hide
import com.miracozkan.yemekhanemenu.util.show
import com.miracozkan.yemekhanemenu.vm.NetworkCallViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : BaseActivity() {

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

        createChannel()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(DATE_PARAM, getCurrentDate())
        runChecker(intent)

    }

    private fun runChecker(intent: Intent) {
        networkCallViewModel.lastMenu.observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                SUCCESS -> {
                    if (edittedCurrentDate.toInt() == it.data?._id) {
                        if (it.data.ogle.isNullOrEmpty()
                            || it.data.ogle.first().menu == ""
                            || it.data.ogle.first().menu == " "
                        ) {
                            runObserve(intent)
                        }
                        networkCallViewModel.updateIsLoadingState(false)
                        startActivity(intent)
                    } else {
                        if (checkConnection()) {
                            runObserve(intent)
                        } else {
                            Toast.makeText(
                                this, getString(R.string.guncel_liste_yok), Toast.LENGTH_SHORT
                            ).show()
                            prgSplash.hide()
                            startActivity(intent)
                        }
                    }
                }
                ERROR -> {
                    networkCallViewModel.updateIsLoadingState(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
                LOADING -> {
                    networkCallViewModel.updateIsLoadingState(true)
                }
            }
        })
    }

    private fun createChannel() {
        NotificationBuilder().createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_title),
            applicationContext
        )
    }

    private fun runObserve(intent: Intent) {
        networkCallViewModel.resultReq.observe(this, androidx.lifecycle.Observer { _response ->
            when (_response.status) {
                LOADING -> {
                    networkCallViewModel.updateIsLoadingState(true)
                }
                SUCCESS -> {
                    networkCallViewModel.updateIsLoadingState(false)
                    startActivity(intent)
                }
                ERROR -> {
                    networkCallViewModel.updateIsLoadingState(false)
                    Toast.makeText(this, _response.message, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
        })

        networkCallViewModel.isLoading.observe(this, androidx.lifecycle.Observer {
            if (it) {
                prgSplash.show()
            } else {
                prgSplash.hide()
            }
        })
    }

    private fun checkConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }

}
