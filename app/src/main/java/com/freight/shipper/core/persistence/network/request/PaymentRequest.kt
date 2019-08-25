package com.freight.shipper.core.persistence.network.request


/**
 * @CreatedBy Sanjay Kushwah on 8/25/2019.
 * sanjaykushwah0601@gmail.com
 */
class PaymentRequest {
    var accountNumber: String = ""
    var bankName: String = ""
    var bankAddress: String = ""
    var wireTransNumber: String = ""
    var currency: String = ""

    override fun toString(): String {
        return "PaymentRequest(accountNumber=$accountNumber, bankName=$bankName, bankAddress=$bankAddress, wireTransNumber=$wireTransNumber, currency=$currency)"
    }
}