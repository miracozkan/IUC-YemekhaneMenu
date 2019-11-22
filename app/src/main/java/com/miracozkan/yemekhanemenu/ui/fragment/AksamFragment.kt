package com.miracozkan.yemekhanemenu.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.base.BaseFragment
import com.miracozkan.yemekhanemenu.datalayer.model.Aksam
import com.miracozkan.yemekhanemenu.util.printMenu
import kotlinx.android.synthetic.main.fragment_aksam.*

class AksamFragment : BaseFragment() {

    private var aksamMenu: Aksam? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            aksamMenu = it.getParcelable("aksamMenu")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aksam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtAksam.text = aksamMenu?.menu.printMenu()
    }

    companion object {
        @JvmStatic
        fun newInstance(aksamMenu: Aksam) =
            AksamFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("aksamMenu", aksamMenu)
                }
            }
    }
}
