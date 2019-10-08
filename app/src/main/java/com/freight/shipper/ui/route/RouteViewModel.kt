package com.freight.shipper.ui.route

import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.RouteRepository


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class RouteViewModel(val activeLoad: ActiveLoad, model: RouteRepository) : BaseViewModel() {
}