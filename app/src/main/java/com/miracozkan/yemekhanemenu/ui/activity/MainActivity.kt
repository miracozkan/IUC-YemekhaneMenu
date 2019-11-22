package com.miracozkan.yemekhanemenu.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.base.BaseActivity
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDatabase
import com.miracozkan.yemekhanemenu.datalayer.model.Aksam
import com.miracozkan.yemekhanemenu.datalayer.model.AllType
import com.miracozkan.yemekhanemenu.datalayer.model.Diyet
import com.miracozkan.yemekhanemenu.datalayer.model.Kahvalti
import com.miracozkan.yemekhanemenu.datalayer.model.Ogle
import com.miracozkan.yemekhanemenu.datalayer.model.Vegan
import com.miracozkan.yemekhanemenu.ui.fragment.AksamFragment
import com.miracozkan.yemekhanemenu.ui.fragment.DiyetFragment
import com.miracozkan.yemekhanemenu.ui.fragment.KahvaltiFragment
import com.miracozkan.yemekhanemenu.ui.fragment.OgleFragment
import com.miracozkan.yemekhanemenu.ui.fragment.VeganFragment
import com.miracozkan.yemekhanemenu.util.DependencyUtil
import com.miracozkan.yemekhanemenu.util.Status
import com.miracozkan.yemekhanemenu.util.Utils.Companion.DATE_PARAM
import com.miracozkan.yemekhanemenu.util.ViewModelFactory
import com.miracozkan.yemekhanemenu.util.hide
import com.miracozkan.yemekhanemenu.util.show
import com.miracozkan.yemekhanemenu.vm.MenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_date.*
import kotlinx.android.synthetic.main.floating_action_button_for_main_activiy.*

class MainActivity : BaseActivity(), CalendarView.OnDateChangeListener, View.OnClickListener {

    private val edittedDate by lazy {
        intent.getStringExtra(DATE_PARAM)!!.replace(".", "").substring(2)
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

    private val bottomSheet by lazy {
        BottomSheetBehavior.from(ltyBottomSheet)
    }

    private var counter = 0
    private lateinit var date: String
    private lateinit var allType: AllType
    private var selectedFragment: String = ""
    private var iconId: Int = R.id.ogleFragment

    private lateinit var kahvaltiMenu: Kahvalti
    private lateinit var ogleMenu: Ogle
    private lateinit var aksamMenu: Aksam
    private lateinit var diyetMenu: Diyet
    private lateinit var veganMenu: Vegan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent?.let {
            date = it.getStringExtra(DATE_PARAM)!!
        }
        getDailyMenus()
        bottomMenu.setItemSelected(R.id.ogleFragment)

        bottomMenu.setOnItemSelectedListener { _it ->
            selectFragment(_it)
        }

        mainActivityFloatingActBar.hide()
        mainActivityFloatingActBar.setOnClickListener(this)

        bottomSheet
            .setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                }

                override fun onStateChanged(p0: View, p1: Int) {
                    when (p1) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            mainActivityFloatingActBar.show()
                        }
                    }
                }
            })

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

    private fun firstOgle() {
        swipeFragment(OgleFragment.newInstance(ogleMenu), "ogle")
    }

    private fun getDailyMenus() {
        menuViewModel.allType.observe(this, Observer { _result ->
            when (_result.status) {
                Status.LOADING -> {
                    mainActivityProgressBar.show()
                }
                Status.SUCCESS -> {
                    _result.data?.let { _it ->
                        allType = _it
                        setMenus(_it)
                        firstOgle()
                    }
                    frgMain.show()
                    mainActivityProgressBar.hide()
                }
                Status.ERROR -> {
                    frgMain.show()
                    mainActivityProgressBar.hide()
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
            R.id.kahvaltiFragment -> {
                iconId = _id
                selectedFragment = "kahvalti"
                swipeFragment(KahvaltiFragment.newInstance(kahvaltiMenu), selectedFragment)
            }
            R.id.ogleFragment -> {
                iconId = _id
                selectedFragment = "ogle"
                swipeFragment(OgleFragment.newInstance(ogleMenu), selectedFragment)
            }
            R.id.aksamFragment -> {
                iconId = _id
                selectedFragment = "aksam"
                swipeFragment(AksamFragment.newInstance(aksamMenu), selectedFragment)
            }
            R.id.diyetFragment -> {
                iconId = _id
                selectedFragment = "diyet"
                swipeFragment(DiyetFragment.newInstance(diyetMenu), selectedFragment)
            }
            R.id.veganFragment -> {
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mainActivityFloatingActBar -> {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                mainActivityFloatingActBar.hide()
            }
        }
    }
}