package com.miracozkan.yemekhanemenu.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Diyet
import kotlinx.android.synthetic.main.fragment_diyet.*

class DiyetFragment : Fragment() {

    private var diyetMenu: Diyet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            diyetMenu = it.getParcelable("diyetMenu")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diyet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtDiyet.text = diyetMenu?.menu
    }

    companion object {
        @JvmStatic
        fun newInstance(diyetMenu: Diyet) =
            DiyetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("diyetMenu", diyetMenu)
                }
            }
    }
}
