package com.freight.shipper.ui.addvehicle

import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.request.AddVehicleRequest
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.persistence.network.response.Image
import com.freight.shipper.core.persistence.network.response.VehicleType
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.services.MyWorkManager
import com.freight.shipper.ui.addvehicle.recyclerview.ImageClickListener
import com.freight.shipper.utils.StringUtil.getString
import com.freight.shipper.utils.serializeToJson
import timber.log.Timber


class AddVehicleViewModel(
    private val model: ProfileRepository
) : AndroidViewModel(FreightApplication.instance), ImageClickListener {

    var vehicleType: MutableLiveData<List<VehicleType>> = model.vehicleTypes
    val newItemClickObserver = ActionLiveData<Boolean>()
    val removeImageObserver = ActionLiveData<Int>()

    val requestModel: AddVehicleRequest by lazy { AddVehicleRequest() }

    var isLoading = ObservableField<Boolean>().apply { set(false) }
    val addVehicleResponse = MutableLiveData<String>()
    var error = MutableLiveData<String>()


    fun submitVehicleDetails() {
        Timber.e(requestModel.toString())
        if (validateFormField()) {
            isLoading.set(true)
            addVehicle()
        }
    }

    // region - Image callbacks
    override fun onImageClicked(image: Image) {
        // TODO //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteButtonClicked(image: Image, position: Int) {
        removeImageObserver.sendAction(position)
    }

    override fun onNewItemClicked() {
        newItemClickObserver.sendAction(true)
    }
    // endregion

    private fun addVehicle() {
        val pojo = serializeToJson(requestModel)
        val data = Data.Builder()
            .putString(MyWorkManager.EXTRA_REQUEST, pojo)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(MyWorkManager::class.java)
            .setInputData(data)
            .build()

        WorkManager.getInstance(getApplication()).enqueue(workRequest)

        WorkManager.getInstance(getApplication()).getWorkInfoByIdLiveData(workRequest.id)
            .observeForever {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    isLoading.set(false)
                    addVehicleResponse.postValue(it.outputData.getString(MyWorkManager.KEY_DATA))
                } else if (it.state == WorkInfo.State.FAILED) {
                    isLoading.set(false)
                    error.postValue(it.outputData.getString(MyWorkManager.KEY_DATA))
                }
                Timber.e(it.toString())
            }
    }

    private fun validateFormField(): Boolean {
        return if (requestModel.height.isNullOrEmpty() || requestModel.images.isEmpty() ||
            requestModel.length.isNullOrEmpty() || requestModel.numberPlate.isNullOrEmpty() ||
            requestModel.regNumber.isNullOrEmpty() || requestModel.vehicleName.isNullOrEmpty() ||
            requestModel.vehicleType.isNullOrEmpty() || requestModel.weight.isNullOrEmpty() ||
            requestModel.width.isNullOrEmpty()
        ) {
            Timber.d(requestModel.vehicleType)
            error.postValue(getString(R.string.all_fields_mandatory_error))
            false
        } else {
//            btnSignupEnable.set(true)
            true
        }
    }

    fun addVehicleImages(vehicleImages: MutableList<Image>) {
        requestModel.images.clear()
        requestModel.images.addAll(vehicleImages.map { it.uri.toString() })
    }
}