package com.miracozkan.yemekhanemenu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Ogle
import com.miracozkan.yemekhanemenu.util.printMenu
import kotlinx.android.synthetic.main.fragment_ogle.*

class OgleFragment : Fragment() {

    private var ogleMenu: Ogle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ogleMenu = it.getParcelable("ogleMenu")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ogle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtOgle.text = ogleMenu?.menu.printMenu()
    }

    companion object {
        @JvmStatic
        fun newInstance(ogleMenu: Ogle) =
            OgleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("ogleMenu", ogleMenu)
                }
            }
    }
}
