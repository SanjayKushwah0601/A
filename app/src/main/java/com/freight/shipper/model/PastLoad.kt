package com.freight.shipper.model


/**
 * @CreatedBy Sanjay Kushwah on 8/12/2019.
 * sanjaykushwah0601@gmail.com
 */

class PastLoad(val id: Long, val customerName: String) {
    val customerImage: String = "http://www.gravatar.com/avatar/?d=identicon"
    val pickCity: String = "Pickup City"
    val destinationCity: String = "Destination City"
    val pickAddress: String = "Pickup Address"
    val destinationAddress: String = "Destination Address"
    val pickTime: String = "10:00 AM, 22 Jul"
    val destinationTime: String = "10:00 AM, 23 Jul"
    val amount: String = "120.00"
    val loadType: String = "Test"
    val goodsName: String = "Electronics"
    val paidBy: String = "Receiver"
    val timeLeftToPickup: String = "00:57 Hrs"
}