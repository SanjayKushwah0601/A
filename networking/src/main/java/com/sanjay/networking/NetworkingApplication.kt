package com.sanjay.networking

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object NetworkingApplication {
    lateinit var context: Context
    fun init(context: Context) {
        if (!this::context.isInitialized) {
            this.context = context
        }
    }
}