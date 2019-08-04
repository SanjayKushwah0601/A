package com.sanjay.networking.response.model.price

/**
 * @author GWL
 */
enum class PurchaseAvailability(val value: String) {
    SAMPLER("sampler"),
    PREMIUM("premium"),
    MEMBERSHIP("membership"),
    UNSPECIFIED("unspecified");

    companion object {
        fun badge(value: PurchaseAvailability): String =
                when (value) {
                    SAMPLER -> SAMPLER.value.toUpperCase()
                    PREMIUM -> PREMIUM.value.toUpperCase()
                    MEMBERSHIP -> MEMBERSHIP.value.toUpperCase()
                    UNSPECIFIED -> UNSPECIFIED.value.toUpperCase()
                }
    }
}