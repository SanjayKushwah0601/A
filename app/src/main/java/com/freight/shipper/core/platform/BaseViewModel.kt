package com.freight.shipper.core.platform

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.freight.shipper.FreightApplication

/**
 * @author GWL
 * @Created on 12/6/19.
 */
abstract class BaseViewModel : ViewModel() {

    fun getString(@StringRes resId: Int): String =
        FreightApplication.instance.getString(resId)
}

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application)