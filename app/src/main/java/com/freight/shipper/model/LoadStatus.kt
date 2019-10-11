package com.freight.shipper.model


/**
 * @CreatedBy Sanjay Kushwah on 10/10/2019.
 * sanjaykushwah0601@gmail.com
 */
enum class LoadStatus(val id: Int) {
    Completed(1),
    Cancelled(2),
    Arrived_And_Waiting(3),
    Awaiting_Driver_to_accept(4),
    Delivery_on_his_way(5),
    Driver_Accepted(6),
    Driver_not_Assign(7),
    Ongoing(8),
    Shipment_is_ready_to_unload(9),
    Uploading_goods(10),
    Waiting_for_Approval(11),
    New_Load(13);

    companion object {
        fun fromId(roleId: Int?): LoadStatus? {
            return when (roleId) {
                Completed.id -> Completed
                Cancelled.id -> Cancelled
                Arrived_And_Waiting.id -> Arrived_And_Waiting
                Awaiting_Driver_to_accept.id -> Awaiting_Driver_to_accept
                Delivery_on_his_way.id -> Delivery_on_his_way
                Driver_Accepted.id -> Driver_Accepted
                Driver_not_Assign.id -> Driver_not_Assign
                Ongoing.id -> Ongoing
                Shipment_is_ready_to_unload.id -> Shipment_is_ready_to_unload
                Uploading_goods.id -> Uploading_goods
                Waiting_for_Approval.id -> Waiting_for_Approval
                New_Load.id -> New_Load
                else -> null
            }
        }
    }
}