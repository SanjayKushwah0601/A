package com.freight.shipper.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseFragment
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.FragmentProfileBinding
import com.freight.shipper.extensions.*
import com.freight.shipper.repository.ProfileRepository
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    var isEdit: Boolean = false
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )
        initViewModel()
        binding.user = viewModel.user
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogout?.setOnClickListener {
            FreightApplication.instance.loginManager.clearDataForLogout()
            startLoginActivity()
        }
        buttonAddVehicle?.setOnClickListener {
            navigateToVehicleList()
        }
        buttonAddShipper?.setOnClickListener {
            navigateToDriverList()
        }
        buttonUploadDocument?.setOnClickListener {
            showConfirmationMessage("Work in process")
        }
        if (isIndividual) {
            buttonAddShipper?.setVisibleIf { false }
        }
    }

    // region - Private functions
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory {
                ProfileViewModel(
                    ProfileRepository(
                        FreightApplication.instance.api, FreightApplication.instance.loginManager
                    )
                )
            }).get(ProfileViewModel::class.java)
    }

    fun onProfileMenuClicked(isEdit: Boolean) {
        this.isEdit = isEdit

    }
    // endregion

}
