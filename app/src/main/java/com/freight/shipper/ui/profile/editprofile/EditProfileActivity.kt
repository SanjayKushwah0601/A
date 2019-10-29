package com.freight.shipper.ui.profile.editprofile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter1
import com.freight.shipper.databinding.ActivityEditProfileBinding
import com.freight.shipper.extensions.onItemSelectedListenerWithoutLabel
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showConfirmationMessage
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class EditProfileActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            ProfileViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(ProfileViewModel::class.java)
    }
    private lateinit var binding: ActivityEditProfileBinding

    private val countryAdapter by lazy {
        object : HintSpinnerAdapter1<Country>(this@EditProfileActivity, mutableListOf()) {
            override fun getLabelFor(item: Country): String {
                return item.countryName
            }

            override fun getKeyFor(item: Country): String {
                return item.countryId
            }
        }
    }

    private val stateAdapter by lazy {
        object : HintSpinnerAdapter1<State>(this@EditProfileActivity, mutableListOf()) {
            override fun getLabelFor(item: State): String {
                return item.stateName
            }
        }
    }

    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.user = viewModel.user
        binding.viewModel = viewModel
        binding.executePendingBindings()
        setupObservers()
        initUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(toolbar, enableUpButton = !isSignUp, title = getString(R.string.edit_profile))
    }

    private fun setupObservers() {
        viewModel.updateProfileResponse.observe(this, Observer {
            // navigateToSignupScreen()
            viewModel.isLoading.set(false)
            Timber.d("Update profile Success")
            // TODO: save details
            setResult(Activity.RESULT_OK)

            showConfirmationMessage(getString(R.string.payment_detail_added_success_msg))
//            if (isSignUp) navigateToDashboard()
//            finish()
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.set(false)
            showErrorMessage(it)
        })

        viewModel.countries.observe(this, Observer { list ->
            spinnerCountry.adapter = countryAdapter
            countryAdapter.setList(list)
            spinnerCountry.onItemSelectedListenerWithoutLabel(countryAdapter) {
                Log.d("SelectedItem", "${it?.countryName}")
                viewModel.getPickStates(it)
                viewModel.user.countryId = it.countryId
            }
            countryAdapter.selectItemById(spinnerCountry, viewModel.user.countryId)
            countryAdapter.selectItemById(spinnerCountry, "50")
        })

        viewModel.states.observe(this, Observer { list ->
            spinnerState?.adapter = stateAdapter
            stateAdapter.setList(list)
            spinnerState?.onItemSelectedListenerWithoutLabel(stateAdapter) {
                Log.d("SelectedItem", "${it}")
                viewModel.user.state = it.stateName
            }
            stateAdapter.selectItemByTitle(spinnerState, viewModel.user.state)
        })
    }
    // endregion

}
