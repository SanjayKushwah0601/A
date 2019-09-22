package com.freight.shipper.model


/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
enum class UserRole(val strValue: String) {
    SHIPPER("individual_shipper"),
    SHIPPER_COMPANY("shipper_company"),
    CUSTOMER("customer");

    companion object {
        fun fromString(role: String?): UserRole? {
            return when (role?.toLowerCase()) {
                SHIPPER.strValue -> SHIPPER
                SHIPPER_COMPANY.strValue -> SHIPPER_COMPANY
                CUSTOMER.strValue -> CUSTOMER
                else -> null
            }
        }
    }
}