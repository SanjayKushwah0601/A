package com.freight.shipper.services

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.freight.shipper.FreightApplication
import com.freight.shipper.core.persistence.network.request.AddVehicleRequest
import com.freight.shipper.core.persistence.network.request.ProgressRequestBody
import com.freight.shipper.core.persistence.network.response.EmptyResponse
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.utils.deserializeFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


/**
 * @CreatedBy Sanjay Kushwah on 9/12/2019.
 * sanjaykushwah0601@gmail.com
 */
class MyWorkManager(val context: Context, val workParams: WorkerParameters) :
    CoroutineWorker(context, workParams) {

    companion object {
        const val EXTRA_REQUEST = "vehicle_request"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val request = workParams.inputData.getString(EXTRA_REQUEST)
            request?.also {
                val data = deserializeFromJson<AddVehicleRequest>(request)
                data?.also {
                    return@withContext uploadAll(data)
                }
            }
            return@withContext Result.failure()
        } catch (e: Exception) {
            return@withContext Result.failure()
        }
    }


    private suspend fun uploadAll(request: AddVehicleRequest): Result {
//        val progressListener = createProgressListener(request)
        val date = Date()
        val timeStamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)


        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("param", "addVehicle")
        builder.addFormDataPart("vehicle_name", request.vehicleName ?: "")
        builder.addFormDataPart("vehicle_type_id", request.vehicleType ?: "")
        builder.addFormDataPart("registration_no", request.regNumber ?: "")
        builder.addFormDataPart("length", request.length ?: "")
        builder.addFormDataPart("width", request.width ?: "")
        builder.addFormDataPart("height", request.height ?: "")
        builder.addFormDataPart("weight", request.weight ?: "")
        builder.addFormDataPart("number_plate", request.numberPlate ?: "")

        request.images.forEachIndexed { index, image ->
            val surveyBody =
                ProgressRequestBody(
                    MediaType.parse("image/*"),
                    Uri.parse(image),
                    context.contentResolver
                )
            builder.addFormDataPart(
                "vehicle_image[]",
                Uri.parse(image).getFileName(context.contentResolver)
                    ?: "${timeStamp}_${index}.png",
                surveyBody
            )
        }

        val requestBody = builder.build()


        val result: APIResult<EmptyResponse> =
            FreightApplication.instance.api.addVehicle(requestBody)
        return when (result) {
            is APIResult.Success -> {
                Timber.e("AddVehicle: Success")
                Result.success()
            }
            is APIResult.Failure -> {
                Timber.e("AddVehicle: Failure")
                Result.failure()
            }
        }


//        val vehicleImagesParts = arrayOf<MultipartBody.Part>()

//        request.images.forEachIndexed { index, image ->
        //            val surveyBody = RequestBody.create(MediaType.parse("image/*"), file)
//            val surveyBody =
//                ProgressRequestBody(MediaType.parse("image/*"), image.uri, contentResolver)
//            vehicleImagesParts[index] =
//                MultipartBody.Part.createFormData(
//                    "vehicle_image", image.uri.getFileName(contentResolver), surveyBody
//                )
//        }
    }

    /* private fun createProgressListener(request: AddVehicleRequest): (Long, Long, Uri) -> Unit {
         var totalSizeOfImages: Long = 0
         var totalSizeUploaded: Long = 0

         val fileUploadProgressMap: MutableMap<Uri, Long> = HashMap()

         //Sum the file sizes so we know the total, and add all URIs to the map at 0 bytes of progress
         request.images.forEach {
             val fileSize = getFileSize(it.uri)
             totalSizeOfImages += fileSize
             fileUploadProgressMap[it.uri] = 0
         }
         //Progress listener - called every time we get a progress update from the API client

         return { uploaded: Long, total: Long, fileUri: Uri ->
             //If the URI is in the progress map, update the total bytes uploaded
             if (fileUploadProgressMap.containsKey(fileUri)) {
                 val previousTotal = fileUploadProgressMap[fileUri] ?: 0
                 totalSizeUploaded += (uploaded - previousTotal)
                 fileUploadProgressMap[fileUri] = uploaded
             }

             //If the file is done, remove it from the list
             if (fileUploadProgressMap.containsKey(fileUri) && uploaded >= total) {
                 fileUploadProgressMap.remove(fileUri)
             }
         }
     }*/

    fun getFileSize(uri: Uri): Int {
        val inputStream = context.contentResolver.openInputStream(uri)
        return inputStream.available()
    }
}

private fun Uri.getFileName(contentResolver: ContentResolver): String? {
    var result: String? = null
    if (scheme == "content") {
        val cursor = contentResolver.query(this, null, null, null, null)
        cursor.use {
            if (it?.moveToFirst() != null) {
                result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }

    val filetypeStart = result?.lastIndexOf('.') ?: -1
    if (filetypeStart == -1) {
        val type = contentResolver.getType(this)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
        result += if (extension?.isNotEmpty() == true) {
            extension
        } else {
            ".png"
        }
    }
    return result
}