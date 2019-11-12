package com.miracozkan.yemekhanemenu.ui.activity

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.model.*
import com.miracozkan.yemekhanemenu.ui.fragment.*
import com.miracozkan.yemekhanemenu.util.*
import com.miracozkan.yemekhanemenu.vm.MenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_date.*


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(), CalendarView.OnDateChangeListener {

    private val edittedDate by lazy {
        intent.getStringExtra("date")!!.replace(".", "").substring(2)
    }

    private val menuRepository by lazy {
        DependencyUtil.getMenuRepository(
            edittedDate,
            ProjectDatabase.getInstance(this).localDataDao()
        )
    }

    private val menuViewModel by lazy {
        ViewModelProviders.of(
            this,
            ViewModelFactory(menuRepository)
        ).get(MenuViewModel::class.java)
    }

    private var counter = 0
    private lateinit var date: String
    private lateinit var allType: AllType
    private var selectedFragment: String = ""
    private var iconId: Int = R.id.kahvalti

    private lateinit var kahvaltiMenu: Kahvalti
    private lateinit var ogleMenu: Ogle
    private lateinit var aksamMenu: Aksam
    private lateinit var diyetMenu: Diyet
    private lateinit var veganMenu: Vegan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent?.let {
            date = it.getStringExtra("date")!!
        }
        getDailyMenus()
        bottomMenu.setItemSelected(R.id.kahvalti)

        bottomMenu.setOnItemSelectedListener { _it ->
            selectFragment(_it)
        }

        calendarView.setOnDateChangeListener(this)

    }

    override fun onSelectedDayChange(p0: CalendarView, year: Int, month: Int, day: Int) {
        when (p0.id) {
            R.id.calendarView -> {
                val editedDay: String = if (day < 10) {
                    "0$day"
                } else {
                    day.toString()
                }
                val editedMonth: String = if (month + 1 < 10) {
                    "0${month + 1}"
                } else {
                    (month + 1).toString()
                }
                date = "$editedDay.$editedMonth.$year"
                setMenus(allType)
                selectFragment(iconId)
            }
        }
    }

    private fun firstKahvalti() {
        swipeFragment(KahvaltiFragment.newInstance(kahvaltiMenu), "kahvalti")
    }

    private fun getDailyMenus() {
        menuViewModel.allType.observe(this, Observer { _result ->
            when (_result.status) {
                Status.LOADING -> {
                    prgBar.show()
                }
                Status.SUCCESS -> {
                    _result.data?.let { _it ->
                        allType = _it
                        setMenus(_it)
                        firstKahvalti()
                    }
                    frgMain.show()
                    prgBar.hide()
                }
                Status.ERROR -> {
                    frgMain.show()
                    prgBar.hide()
                    Toast.makeText(this, _result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setMenus(allType: AllType) {
        kahvaltiMenu = findMenu(allType.kahvalti!!, date)
        ogleMenu = findMenu(allType.ogle!!, date)
        aksamMenu = findMenu(allType.aksam!!, date)
        veganMenu = findMenu(allType.vegan!!, date)
        diyetMenu = findMenu(allType.diyet!!, date)
    }

    private fun <T> findMenu(list: List<T>, selectedDate: String): T {
        when (list[0]) {
            is Kahvalti -> {
                list.forEach {
                    it as Kahvalti
                    if (it.tarih == selectedDate) {
                        return it
                    }
                }
                return Kahvalti() as T
            }
            is Aksam -> {
                list.forEach {
                    it as Aksam
                    if (it.tarih == selectedDate) {
                        return it
                    }
                }
                return Aksam() as T
            }
            is Ogle -> {
                list.forEach {
                    it as Ogle
                    if (it.tarih == selectedDate) {
                        return it
                    }
                }
                return Ogle() as T
            }
            is Diyet -> {
                list.forEach {
                    it as Diyet
                    if (it.tarih == selectedDate) {
                        return it
                    }
                }
                return Diyet() as T
            }
            is Vegan -> {
                list.forEach {
                    it as Vegan
                    if (it.tarih == selectedDate) {
                        return it
                    }
                }
                return Vegan() as T
            }
            else -> return Kahvalti() as T
        }
    }

    private fun selectFragment(_id: Int) {
        when (_id) {
            R.id.kahvalti -> {
                iconId = _id
                selectedFragment = "kahvalti"
                swipeFragment(KahvaltiFragment.newInstance(kahvaltiMenu), selectedFragment)
            }
            R.id.ogle -> {
                iconId = _id
                selectedFragment = "ogle"
                swipeFragment(OgleFragment.newInstance(ogleMenu), selectedFragment)
            }
            R.id.aksam -> {
                iconId = _id
                selectedFragment = "aksam"
                swipeFragment(AksamFragment.newInstance(aksamMenu), selectedFragment)
            }
            R.id.diyet -> {
                iconId = _id
                selectedFragment = "diyet"
                swipeFragment(DiyetFragment.newInstance(diyetMenu), selectedFragment)
            }
            R.id.vegan -> {
                iconId = _id
                selectedFragment = "vegan"
                swipeFragment(VeganFragment.newInstance(veganMenu), selectedFragment)
            }
            else -> {
                Toast.makeText(this, "Wrong Selection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun swipeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frgMain, fragment, selectedFragment)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (counter == 0) {
            counter++
            Toast.makeText(this, "Cikmak Icin Bir Daha Tıklayiniz...", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }
}