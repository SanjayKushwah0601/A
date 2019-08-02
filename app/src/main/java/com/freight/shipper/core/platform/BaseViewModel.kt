package com.freight.shipper.core.platform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

/**
 * @author GWL
 * @Created on 12/6/19.
 */
abstract class BaseViewModel : ViewModel() {

    //region - Exception handling
//    open fun parseException(exception: APIError, @StringRes defaultMessageId: Int) {
//
//    }
    //endregion
}

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application)