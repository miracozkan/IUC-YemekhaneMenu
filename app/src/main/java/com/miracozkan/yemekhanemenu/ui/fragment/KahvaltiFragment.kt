package com.miracozkan.yemekhanemenu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R

class KahvaltiFragment : Fragment() {

    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString("date")
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

    /**
     * newInstance unused -> kahvaltı empty dönüyor
     */
    companion object {
        @JvmStatic
        fun newInstance(date: String) =
            KahvaltiFragment().apply {
                arguments = Bundle().apply {
                    putString("date", date)
                }
            }
    }
}
