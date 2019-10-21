package com.miracozkan.yemekhanemenu.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Aksam
import kotlinx.android.synthetic.main.fragment_aksam.*

class AksamFragment : Fragment() {

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
        txtAksam.text = aksamMenu?.menu

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
