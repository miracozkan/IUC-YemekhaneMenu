package com.miracozkan.yemekhanemenu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Kahvalti
import com.miracozkan.yemekhanemenu.util.printMenu
import kotlinx.android.synthetic.main.fragment_kahvalti.*

class KahvaltiFragment : Fragment() {

    private var kahvaltiMenu: Kahvalti? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            kahvaltiMenu = it.getParcelable("kahvaltiMenu")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_kahvalti,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtKahvalti.text = kahvaltiMenu?.menu.printMenu()

    }

    /**
     * newInstance unused -> kahvaltı empty dönüyor
     */
    companion object {
        @JvmStatic
        fun newInstance(kahvaltiMenu: Kahvalti) =
            KahvaltiFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("kahvaltiMenu", kahvaltiMenu)
                }
            }
    }
}
