package com.miracozkan.yemekhanemenu.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Vegan
import kotlinx.android.synthetic.main.fragment_vegan.*

class VeganFragment : Fragment() {

    private var veganMenu: Vegan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            veganMenu = it.getParcelable("veganMenu")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vegan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtVegan.text = veganMenu?.menu
    }

    companion object {
        @JvmStatic
        fun newInstance(veganMenu: Vegan) =
            VeganFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("veganMenu", veganMenu)
                }
            }
    }
}
