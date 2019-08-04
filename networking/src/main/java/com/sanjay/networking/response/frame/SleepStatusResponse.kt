package com.sanjay.networking.response.frame

class SleepStatusResponse(status: String, response: Boolean): BaseFrameResponse<Boolean>(status, response) {
    val isSleeping: Boolean
        get() { return response }
}