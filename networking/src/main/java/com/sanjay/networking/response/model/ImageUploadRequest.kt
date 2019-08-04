package com.sanjay.networking.response.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CropPoints(val x1: Int, val x2: Int, val y1: Int, val y2: Int) : Parcelable


@Parcelize
data class ImageUpload(val uri: Uri, val cropPoints: CropPoints?) : Parcelable

@Parcelize
data class ImageUploadRequest(val list: List<ImageUpload>) : Parcelable