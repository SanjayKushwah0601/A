package com.freight.shipper.ui.profile.addvehicle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.request.AddVehicleRequest
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.core.platform.NonNullObserver
import com.freight.shipper.databinding.ActivityAddVehicleBinding
import com.freight.shipper.extensions.*
import com.freight.shipper.model.Image
import com.freight.shipper.model.VehicleType
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.services.AddVehicleWorkManager
import com.freight.shipper.ui.profile.addvehicle.recyclerview.ImageAdapter
import kotlinx.android.synthetic.main.activity_add_vehicle.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber


class AddVehicleActivity : BaseActivity() {

    // region - Companion
    companion object {
        const val REQUEST_CODE_GALLERY = 1
    }
    // endregion

    // region - Private fields
    private val viewModel: AddVehicleViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            AddVehicleViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(AddVehicleViewModel::class.java)
    }
    private lateinit var binding: ActivityAddVehicleBinding
    private lateinit var adapter: ImageAdapter

    private val vehicleTypeAdapter by lazy {
        object : HintSpinnerAdapter<VehicleType>(
            this@AddVehicleActivity, mutableListOf(),
            getString(R.string.select_vehicle_type)
        ) {
            override fun getLabelFor(item: VehicleType): String {
                return item.vehicleType
            }
        }
    }
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_vehicle)
        binding.viewModel = viewModel
        initUI()
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_GALLERY -> onGalleryReceived(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(
            toolbar, enableUpButton = true,
            title = getString(R.string.company),
            subTitle = getString(R.string.add_vehicle)
        )
        setAdapter()
    }

    private fun setupObservers() {
        // used to show gallery view on choose picture button clicked
        viewModel.newItemClickObserver.observe(this, object : NonNullObserver<Boolean>() {
            override fun onNonNullChanged(value: Boolean) {
                showImageChooser(REQUEST_CODE_GALLERY)
            }
        })
        viewModel.removeImageObserver.observe(this, object : NonNullObserver<Int>() {
            override fun onNonNullChanged(value: Int) {
                adapter.removeImage(value)
            }
        })
        viewModel.vehicleType.observeForever { list ->
            spVehicleType.adapter = vehicleTypeAdapter
            vehicleTypeAdapter.setList(list)
            spVehicleType.setOnItemSelectListener(vehicleTypeAdapter) {
                Log.d("SelectedItem", "${it?.vehicleType}")
                viewModel.requestModel.vehicleType = it.vehicleTypeId

                dimensionsContainer?.setVisibleIf { true }
                etWidth?.setText(it.width)
                etHeight?.setText(it.height)
                etLength?.setText(it.length)
                etCapacity?.setText(it.weight)
            }
        }
        viewModel.addVehicleResponse.observe(this, Observer {
            // navigateToSignupScreen()
            viewModel.isLoading.set(false)
            showConfirmationMessage(getString(R.string.vehicle_added_success_message))
            Timber.d("Payment Submit Success")
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.set(false)
            showErrorMessage(it)
        })
    }

    private fun onGalleryReceived(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                if (adapter.artworks.size > 2) {
                    showErrorMessage(getString(R.string.only_images_are_allowed))
                    return
                }
                val uri = data.data
                uri?.also {
                    adapter.addImage(Image(it))
                }
                viewModel.addVehicleImages(adapter.artworks)
//                navigateToCropScreen(arrayListOf(uri), REQUEST_CODE_CROP)
            } else if (data?.clipData != null) {
                if (adapter.artworks.size > 2) {
                    showErrorMessage(getString(R.string.only_images_are_allowed))
                    return
                }

                val items: MutableList<Uri> = mutableListOf()


                for (n in 0 until data.clipData.itemCount) {
                    val clipData = data.clipData.getItemAt(n)
                    items.add(clipData.uri)
                }
                adapter.addImages(items.map { Image(it) })
                viewModel.addVehicleImages(adapter.artworks)
            }
        }
    }


    private fun addVehicle(list: MutableList<Image>) {
        val imageUploadIntent = Intent()
        imageUploadIntent.putExtra(
            AddVehicleWorkManager.EXTRA_REQUEST,
            AddVehicleRequest().apply { images.addAll(list.map { it.uri.toString() }) })
        JobIntentService.enqueueWork(this, AddVehicleWorkManager::class.java, 6, imageUploadIntent)
    }

    private fun setAdapter() {
        adapter = ImageAdapter(viewModel, hideDeleteButtons = false, hideAddButton = false)
        recyclerView.layoutManager =
            GridLayoutManager(this@AddVehicleActivity, 2)
        recyclerView.adapter = adapter
    }
    // endregion
}
