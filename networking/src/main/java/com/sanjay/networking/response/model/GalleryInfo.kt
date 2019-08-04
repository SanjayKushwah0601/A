package com.sanjay.networking.response.model


import com.google.gson.annotations.SerializedName

class GalleryInfo {
    @SerializedName("current_gallery")
    var galleryId: Long = -1
}
