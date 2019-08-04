package com.sanjay.networking.request

data class UserFeedback(
        val rating: Int,
        val source: String,
        val message: String,
        val version: String,
        val contact: Boolean)