package com.sanjay.networking.response.model.price

import java.lang.Math.round
import java.text.NumberFormat
import java.util.*

/**
 * @author GWL
 */
interface Pricing {
    companion object {
        fun convert(pennies: Long): String {
            val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
            currencyFormatter.isGroupingUsed = true
            val price = pennies.toDouble() / 100.0
            return currencyFormatter.format(price)
        }

        fun createPercentageNumber(nonMember: Long, member: Long): Int {
            if (nonMember < 1) {
                return 0
            }
            val percentage = (nonMember.toDouble() - member.toDouble()) / nonMember.toDouble() * 100
            val rounded = round(percentage)
            return rounded.toInt()
        }
    }
}