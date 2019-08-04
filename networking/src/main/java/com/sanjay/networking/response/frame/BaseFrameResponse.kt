package com.sanjay.networking.response.frame

abstract class BaseFrameResponse<T>(var status: String, var response: T) {
    val isSuccessful: Boolean
        get() { return "pass".equals(status, true) }
}