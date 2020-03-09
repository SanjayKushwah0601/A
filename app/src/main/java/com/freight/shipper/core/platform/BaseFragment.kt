package com.freight.shipper.core.platform

import androidx.fragment.app.Fragment
import com.freight.shipper.FreightApplication
import com.freight.shipper.model.UserRole

/**
 * @author Sanjay Kushwah
 * @Created on 12/6/19.
 */
abstract class BaseFragment : Fragment() {
    val isIndividual by lazy { FreightApplication.instance.loginManager.userRole == UserRole.SHIPPER }
}