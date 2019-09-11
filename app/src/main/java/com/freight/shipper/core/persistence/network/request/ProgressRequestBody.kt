package com.freight.shipper.core.persistence.network.request

import android.content.ContentResolver
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

class ProgressRequestBody(
    val mediaType: MediaType?, val uri: Uri,
    val contentResolver: ContentResolver,
    val listener: ((uploaded: Long, total: Long, uri: Uri) -> Unit)? = null
) : RequestBody() {
    override fun contentType(): MediaType? {
        return mediaType
    }

    override fun writeTo(sink: BufferedSink?) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = contentResolver.openInputStream(uri)
        fileInputStream?.also {
            val fileLength = it.available().toLong()
            var totalWritten: Long = 0
            GlobalScope.launch(Dispatchers.Main) {
                listener?.invoke(totalWritten, fileLength, uri)
            }
            var length = it.read(buffer)
            while (length != -1) {
                sink?.write(buffer, 0, length)
                totalWritten += length.toLong()
                GlobalScope.launch(Dispatchers.Main) {
                    listener?.invoke(totalWritten, fileLength, uri)
                }
                length = it.read(buffer)
            }
        }
    }
}