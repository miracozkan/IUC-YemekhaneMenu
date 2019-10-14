package com.miracozkan.yemekhanemenu.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.model.*
import com.miracozkan.yemekhanemenu.ui.fragment.*
import com.miracozkan.yemekhanemenu.util.*
import com.miracozkan.yemekhanemenu.vm.MenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_date.*

class MainActivity : AppCompatActivity(), CalendarView.OnDateChangeListener,
    OgleFragment.OnDataPass {

    private val menuRepository by lazy {
        DependencyUtil.getMenuRepository(ProjectDatabase.getInstance(this).localDataDao())
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
    private var selectedFragmentId: Int = -1
    private var iconId: Int = -1
    private val errorMessage = "Hafta Sonu veya Resmi Tatil Sectiniz..."

    /**
     * Kahvalti empty dönüyor
     */

    private lateinit var kahvaltiMenu: Kahvalti
    private lateinit var ogleMenu: Ogle
    private lateinit var aksamMenu: Aksam
    private lateinit var diyetMenu: Diyet
    private lateinit var veganMenu: Vegan

    private val bottomSheet by lazy {
        BottomSheetBehavior.from(ltyBottomSheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent?.let {
            date = it.getStringExtra("date")!!
        }

        bottomMenu.setItemSelected(R.id.kahvalti)
        swipeFragment(KahvaltiFragment(), "kahvalti")

        getDailyMenus()

        bottomMenu.setOnItemSelectedListener { _it ->
            selectFragment(_it)
        }

        calendarView.setOnDateChangeListener(this)

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            @SuppressLint("SwitchIntDef", "SimpleDateFormat")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {//Acıldı
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {//Kapandı
                    }
                }
            }
        })
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

    private fun getDailyMenus() {
        menuViewModel.allType.observe(this, Observer { _result ->
            when (_result.status) {
                Status.LOADING -> {
                    //frgMain XML'den invisible yapıldı
                    prgBar.show()
                }
                Status.SUCCESS -> {

                    _result.data?.let { _it ->
                        allType = _it
                        setMenus(_it)
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
        kahvaltiMenu = findKahvalti()!!
        ogleMenu = findOgle(allType.ogle!!, date)!!
        aksamMenu = findAksam(allType.aksam!!, date)!!
        veganMenu = findVegan(allType.vegan!!, date)!!
        diyetMenu = findDiyet(allType.diyet!!, date)!!
    }

    //TODO Takvimden haftasonu seçildiginde  her ögün için mesaj cıkarılıyor -> setMenus()

    private fun findOgle(list: List<Ogle>, selectedDate: String): Ogle? {
        list.forEach {
            if (it.tarih == selectedDate) {
                return it
            }
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            .show()
        return Ogle(menu = errorMessage)
    }

    private fun findKahvalti(): Kahvalti? {
        return Kahvalti()
    }

    private fun findAksam(list: List<Aksam>, selectedDate: String): Aksam? {
        list.forEach {
            if (it.tarih == selectedDate) {
                return it
            }
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            .show()
        return Aksam(menu = errorMessage)
    }

    private fun findDiyet(list: List<Diyet>, selectedDate: String): Diyet? {
        list.forEach {
            if (it.tarih == selectedDate) {
                return it
            }
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            .show()
        return Diyet(menu = errorMessage)
    }

    private fun findVegan(list: List<Vegan>, selectedDate: String): Vegan? {
        list.forEach {
            if (it.tarih == selectedDate) {
                return it
            }
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            .show()
        return Vegan(menu = errorMessage)
    }

    //TODO Ilk acilista(kahvalti) -> date seçiminde _id gelmedigi için else düşüyor

    private fun selectFragment(_id: Int) {
        when (_id) {
            R.id.kahvalti -> {
                iconId = _id
                selectedFragment = "kahvalti"
                swipeFragment(KahvaltiFragment(), selectedFragment)

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

    override fun onDataPass(int: Int, string: String) {
        selectedFragmentId = int
        Log.e("Fragment ID ", int.toString())
        Log.e("Fragment Tag ", string)
    }
}