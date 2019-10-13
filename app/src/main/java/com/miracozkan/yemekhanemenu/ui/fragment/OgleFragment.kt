package com.miracozkan.yemekhanemenu.ui.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miracozkan.yemekhanemenu.R
import com.miracozkan.yemekhanemenu.datalayer.model.Ogle
import kotlinx.android.synthetic.main.fragment_ogle.*

class OgleFragment : Fragment() {

    private var listener: OnDataPass? = null
    private var date: Ogle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getParcelable("date")
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
        onDataPass()

        txtData.text = date?.menu
    }

    private fun onDataPass() {
        listener?.onDataPass(
            this.id,
            this.tag!!
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDataPass) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnDataPass {
        fun onDataPass(int: Int, string: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(date: Ogle) =
            OgleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("date", date)
                }
            }
    }
}
