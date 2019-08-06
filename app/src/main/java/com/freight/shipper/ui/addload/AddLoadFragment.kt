package com.freight.shipper.ui.addload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R

class AddLoadFragment : Fragment() {

    companion object {
        fun newInstance() = AddLoadFragment()
    }

    private lateinit var viewModel: AddLoadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_load, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddLoadViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
