package com.sanjay.networking.client.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanjay.networking.annotations.OrientationString
import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.request.UserFeedback
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.*
import com.sanjay.networking.result.APIError
import com.sanjay.networking.result.APIErrorType
import com.sanjay.networking.result.APIResult
import com.sanjay.networking.service.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MultipartBody
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import timber.log.Timber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


class MeuralAPI(val retrofit: Retrofit) : MeuralAPIContract() {

    // region - Service init
    private val articleService = retrofit.create(ArticleService::class.java)
    private val artistService = retrofit.create(ArtistService::class.java)
    private val authService = retrofit.create(AuthenticationService::class.java)
    private val categoryService = retrofit.create(CategoryService::class.java)
    private val channelService = retrofit.create(ChannelService::class.java)
    private val deviceService = retrofit.create(DeviceService::class.java)
    private val feedService = retrofit.create(FeedService::class.java)
    private val galleryService = retrofit.create(GalleryService::class.java)
    private val groupService = retrofit.create(GroupService::class.java)
    private val itemService = retrofit.create(ImageService::class.java)
    private val userService = retrofit.create(UserService::class.java)
    private val searchService = retrofit.create(SearchService::class.java)
    // endregion

    private val decoder = Gson()

    override suspend fun getArticle(id: Long): APIResult<Article> {
        return articleService.getArticle(id).apiResult()
    }

    override suspend fun getArticleItems(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Image>> {
        return itemService.getModelItems("articles", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getArticlesIn(model: String, id: Long, page: Int, count: Int, sort: Sort?, filter: Filter?): APIResult<List<Article>> {
        return articleService.getModelArticles(model, id, page, count, sort, filter).apiResult()
    }
    // endregion

    // region - Artist
    override suspend fun getArtist(id: Long): APIResult<Artist> {
        return artistService.getArtist(id).apiResult()
    }

    override suspend fun getArtists(page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Artist>> {
        return artistService.getArtists(page, count, sort, filter).apiResult()
    }

    override suspend fun getArtistItems(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Image>> {
        return itemService.getModelItems("artists", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getArtistGalleries(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Gallery>> {
        return galleryService.getModelGalleries("artists", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getArtistsIn(model: String, id: Long, page: Int, count: Int, sort: Sort?, filter: Filter?): APIResult<List<Artist>> {
        return artistService.getModelArtists(model, id, page, count, sort, filter).apiResult()
    }
    // endregion

    // region - Auth
    override suspend fun login(email: String, password: String): APIResult<Token> {
        return authService.authenticate(email, password).mappedApiResult()
    }

    override suspend fun register(firstName: String, lastName: String, email: String, password: String,
                                  confirmPassword: String, countryCode: String, isReceiveCommunications: Boolean,
                                  isSecurityToken: Boolean): APIResult<Token> {
        return userService.createUser(firstName, lastName, email, password, confirmPassword, countryCode,
                isReceiveCommunications, isSecurityToken).apiResult()
    }
    // endregion

    // region - Category
    override suspend fun getCategory(id: Long): APIResult<Category> {
        return categoryService.getCategory(id).apiResult()
    }

    override suspend fun getCategoryArtists(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Artist>> {
        return artistService.getModelArtists("categories", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getCategoryGalleries(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Gallery>> {
        return galleryService.getModelGalleries("categories", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getCategoryItems(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Image>> {
        return itemService.getModelItems("categories", id, page, count, sort, filter).apiResult()
    }
    // endregion

    // region - Channel
    override suspend fun getChannel(id: Long): APIResult<Channel> {
        return channelService.getChannel(id).apiResult()
    }

    override suspend fun getChannels(page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Channel>> {
        return channelService.getChannels(page, count, sort, filter).apiResult()
    }

    override suspend fun getChannelItems(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Image>> {
        return itemService.getModelItems("channels", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getChannelGalleries(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Gallery>> {
        return galleryService.getModelGalleries("channels", id, page, count, sort, filter).apiResult()
    }

    override suspend fun getChannelArtists(id: Long, page: Int?, count: Int?, sort: Sort?, filter: Filter?): APIResult<List<Artist>> {
        return artistService.getModelArtists("channels", id, page, count, sort, filter).apiResult()
    }
    // endregion

    // region - Device
    override suspend fun getDevice(id: Long): APIResult<Device> {
        return deviceService.getCanvas(id).apiResult()
    }

    override suspend fun getGalleriesFor(deviceId: Long): APIResult<List<Gallery>> {
        return deviceService.getDeviceGalleries(deviceId).apiResult()
    }

    override suspend fun deleteDevice(id: Long): APIResult<Unit> {
        return deviceService.deleteCanvas(id).executeEmptyResponseCallAsync()
    }

    override suspend fun performCommand(id: Long, deviceCommand: DeviceCommand): APIResult<Unit> {
        return deviceService.sendCommand(id, deviceCommand).apiResult()
    }

    override suspend fun setSchedulerEnabled(deviceId: Long, schedulerEnabled: Boolean): APIResult<Device> {
        return deviceService.setSchedulerEnabled(deviceId, schedulerEnabled).apiResult()
    }

    override suspend fun resync(id: Long, timeout: Int) {
        deviceService.syncCanvas(id)
    }

    override suspend fun addGalleryToCanvas(deviceId: Long, galleryId: Long): APIResult<Device> {
        return deviceService.addGalleryToCanvas(deviceId, galleryId).apiResult()
    }

    override suspend fun previewImageOnCanvas(deviceId: Long, imageId: Long): APIResult<Unit> {
        return deviceService.previewItemOnCanvas(deviceId, imageId).apiResult()
    }

    override suspend fun getAllWorks(deviceId: Long, page: Int?, count: Int?, sort: Sort?): APIResult<List<Image>> {
        return deviceService.getAllWorks(deviceId, page, count, sort).apiResult()
    }

    override suspend fun addAllWorksItem(deviceId: Long, itemId: Long): APIResult<Device> {
        return deviceService.addAllWorksItem(deviceId, itemId).apiResult()
    }

    override suspend fun deleteGalleryFromCanvas(deviceId: Long, galleryId: Long): APIResult<Device> {
        return deviceService.deleteGalleryFromCanvas(deviceId, galleryId).apiResult()
    }

    override suspend fun removeAllWorksItem(deviceId: Long, workId: Long): APIResult<Device> {
        return deviceService.removeAllWorksItem(deviceId, workId).apiResult()
    }

    override suspend fun updateCanvas(deviceId: Long, newSettings: DeviceOptions): APIResult<Device>? {
        return deviceService.updateCanvas(deviceId, newSettings).apiResult()
    }

    override suspend fun getDeviceLanguages(): APIResult<List<Language>> {
        return deviceService.getDeviceLanguages().apiResult()
    }
    // endregion

    // region - Favorite
    override suspend fun favorite(model: String, id: Long, isFavorite: Boolean): APIResult<Boolean> {
        return if (isFavorite) {
            userService.favorite(model, id).apiResult().map { isFavorite }
        } else {
            userService.unfavorite(id, model).emptyBodyResult().map { isFavorite }
        }
    }

    override suspend fun getFavoriteArtists(page: Int, count: Int): APIResult<List<Artist>>? {
        return userService.getFavoriteArtists(page, count).apiResult()
    }

    override suspend fun getFavoriteCategories(page: Int, count: Int): APIResult<List<Category>>? {
        return userService.getFavoriteCategories(page, count).apiResult()
    }

    override suspend fun getFavoriteChannels(page: Int, count: Int): APIResult<List<Channel>>? {
        return userService.getFavoriteChannels(page, count).apiResult()
    }

    override suspend fun getFavoriteGalleries(page: Int, count: Int): APIResult<List<Gallery>>? {
        return userService.getFavoriteGalleries(page, count).apiResult()
    }

    override suspend fun getFavoriteImages(page: Int, count: Int): APIResult<List<Image>>? {
        return userService.getFavoriteItems(page, count).apiResult()
    }

    override suspend fun getUserGalleries(page: Int, count: Int, sort: Sort?)
            : APIResult<List<Gallery>> {
        return userService.getUserGalleries(page, count, sort = sort).apiResult()
    }
    // endregion

    // region - Feed
    override suspend fun getFeed(page: Int, count: Int): APIResult<List<FeedItem>> {
        return feedService.getFeed(page, count).apiResult()
    }
    // endregion

    // region - Gallery
    override suspend fun getGallery(id: Long): APIResult<Gallery> {
        return galleryService.getGallery(id).apiResult()
    }

    override suspend fun getGalleryItems(galleryId: Long, page: Int?, count: Int?): APIResult<List<Image>> {
        return itemService.getModelItems("galleries", galleryId, page, count, null, null).apiResult()
    }

    override suspend fun addItemToGallery(galleryId: Long, itemId: Long): APIResult<Gallery> {
        return galleryService.addItemToGallery(galleryId, itemId).apiResult()
    }

    override suspend fun removeItemFromGallery(galleryId: Long, itemId: Long): APIResult<Unit> {
        return galleryService.removeItemFromGallery(galleryId, itemId).apiResult()
    }

    override suspend fun deleteGallery(galleryId: Long): APIResult<Unit> {
        return galleryService.deleteGallery(galleryId).emptyBodyResult()
    }

    override suspend fun sortGallery(galleryId: Long, order: List<Long>): APIResult<Gallery> {
        return galleryService.sortGallery(galleryId, order).apiResult()
    }

    override suspend fun getGalleriesIn(model: String, id: Long, page: Int, count: Int, sort: Sort?, filter: Filter?): APIResult<List<Gallery>> {
        return galleryService.getModelGalleries(model, id, page, count, sort, filter).apiResult()
    }

    override suspend fun createGallery(name: String, description: String,
                                       @OrientationString orientation: String?): APIResult<Gallery> {
        return galleryService.createGallery(name, description, orientation).apiResult()
    }

    override suspend fun updateGallery(galleryId: Long, name: String, description: String,
                                       @OrientationString orientation: String?, coverId: Long?): APIResult<Gallery> {
        return galleryService.updateGallery(galleryId, name, description, orientation, coverId).apiResult()
    }
    // endregion

    override suspend fun getGroups(): APIResult<List<Group>> {
        return groupService.getGroups().apiResult()
    }

    override suspend fun getGroupCategories(id: Long): APIResult<List<Category>> {
        return groupService.getGroupCategories(id).apiResult()
    }
    // endregion

    // region - Item
    override suspend fun getItem(id: Long): APIResult<Image> {
        return itemService.getImage(id).apiResult()
    }

    override suspend fun uploadItem(item: MultipartBody.Part): APIResult<Image> {
        return itemService.uploadImage(item).apiResult()
    }

    override suspend fun recropItem(itemId: Long, cropPoints: CropPoints): APIResult<Image> {
        return itemService.cropImage(itemId, cropPoints).apiResult()
    }

    override suspend fun updateImageData(imageId: Long, title: String, author: String, description: String, medium: String?, year: String): APIResult<Image> {
        return itemService.updateImageData(imageId, title, author, description, medium, year).apiResult()
    }

    override suspend fun getItemsIn(model: String, id: Long, page: Int, count: Int, sort: Sort?, filter: Filter?): APIResult<List<Image>> {
        return itemService.getModelItems(model, id, page, count, sort, filter).apiResult()
    }

    override suspend fun deleteImage(imageId: Long): APIResult<Unit> {
        return itemService.deleteImage(imageId).executeEmptyResponseCallAsync()
    }
    // endregion

    // region - User
    override suspend fun getUser(): APIResult<User> {
        return userService.getCurrentUser().apiResult()
    }

    override suspend fun getUserCanvases(): APIResult<List<Device>> {
        return userService.getUserCanvases().apiResult()
    }

    override suspend fun getUserWithCanvases(): APIResult<UserWithCanvases> {
        val userResult = getUser()
        when (userResult) {
            is APIResult.Failure -> return APIResult.Failure(userResult.details)
            is APIResult.Success -> {
                val canvasesResult = getUserCanvases()
                return canvasesResult.map {
                    UserWithCanvases(userResult.response.data, it)
                }
            }
        }
    }

    override suspend fun submitFeedback(userFeedback: UserFeedback): APIResult<Unit> {
        return userService.submitFeedBack(userFeedback).apiResult()
    }

    override suspend fun getUserPurchasedGalleries(): APIResult<List<Gallery>> {
        return userService.getUserPurchasedGalleries().apiResult()
    }

    override suspend fun getUserPurchasedWorks(): APIResult<List<Image>> {
        return userService.getUserPurchasedWorks().apiResult()
    }

    override suspend fun purchaseGallery(contentId: Long): APIResult<Purchase<Gallery>> {
        return userService.purchaseGallery(contentId).apiResult()
    }

    override suspend fun purchaseImage(contentId: Long): APIResult<Purchase<Image>> {
        return userService.purchaseImage(contentId).apiResult()
    }

    override suspend fun addUserCanvas(productKey: String): APIResult<Device> {
        return userService.addUserCanvas(productKey).apiResult()
    }

    override suspend fun getuserImages(page: Int): APIResult<List<Image>>? {
        return userService.getUserImages(page).apiResult()
    }

    // endregion

    // region - Search
    override suspend fun search(searchTerm: String, page: Int?, count: Int?): APIResult<SearchResults> {
        return searchService.search(searchTerm, page, count).apiResult()
    }
    // endregion

    // region - Helpers
    private suspend fun Call<ApiResponse<Unit>>.emptyBodyResult(): APIResult<Unit> {
        return try {
            awaitResult()
            APIResult.Success(Unit)
        } catch (error: Throwable) {
            if (error is NullPointerException) {
                APIResult.Success(Unit)
            } else {
                APIResult.Failure(errorFromAnyException(error))
            }
        }
    }

    private suspend inline fun Call<ApiResponse<Unit>>.executeEmptyResponseCallAsync(): APIResult<Unit> {
        Timber.v("Executing empty response call async")
        var error: APIResult<Unit>? = null
        val result: Response<ApiResponse<Unit>>? = GlobalScope.async {
            try {
                this@executeEmptyResponseCallAsync.execute()
            } catch (exception: Throwable) {
                when (exception) {
                    is HttpException ->
                        error = APIResult.Failure(errorFromHttpException(exception))

                    else -> {
                        error = APIResult.Failure(errorFromAnyException(exception))
                    }
                }
                null
            }
        }.await()

        return if (result?.isSuccessful == true) {
            APIResult.Success(Unit)
        } else {
            APIResult.Failure(APIError.default)
        }
    }

    private suspend fun <T : Any> Call<T>.mappedApiResult(): APIResult<T> {
        val result = awaitResult()
        return when (result) {
            is Result.Ok -> APIResult.Success(ApiResponse(result.value, null, null, null))
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }

    // region - Response Parsing
    private suspend fun <T : Any> Call<ApiResponse<T>>.apiResult(): APIResult<T> {
        val result = awaitResult()
        return when (result) {
            is Result.Ok -> APIResult.Success(result.value)
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }
    // endregion

    // region - Error handling
    private fun errorFromHttpException(exception: HttpException): APIError {
        val errorBody = exception.response().errorBody()?.string()
        val code = exception.code()
        val message = exception.message()

        return if (errorBody != null) {
            if (JSONTokener(errorBody).nextValue() !is JSONObject)
                return APIError(mapOf(), APIErrorType.General, code, exception, message)

            val errors: HashMap<String, Any?> = decoder.fromJson(errorBody, errorMapType)
            val stringError: HashMap<String, String> = HashMap(errors.mapValues {
                it.value?.toString() ?: ""
            })
            val type = errorTypeFrom(stringError)
            val returnedMessage = messageFrom(stringError) ?: message
            val errorResult = APIError(stringError, type, code, exception, returnedMessage)
            errorResult
        } else {
            APIError(mapOf(), APIErrorType.General, code, exception, message)
        }
    }

    private fun errorFromAnyException(exception: Throwable): APIError {
        val (typeString, reason) = when (exception) {
            is SocketTimeoutException,
            is SocketException,
            is UnknownHostException -> Pair("Network", "Network error")
            else -> Pair("NetworkCall", "An error occurred")
        }
        return APIError(hashMapOf(typeString to reason), APIErrorType.Network(exception), -1, exception, reason)
    }

    private fun errorTypeFrom(errors: HashMap<String, String>): APIErrorType {
        /*
         * In APIClient there were Exception handling for subscription
         * for make same handling, return  APIErrorType.Subscription.
        */
        if (errors.contains("subscription")) {
            return APIErrorType.Subscription
        } else if (errors.contains("purchase")) {
            return APIErrorType.Purchase
        } else if (errors.contains("firstName") ||
                errors.contains("lastName") ||
                errors.contains("email") ||
                errors.contains("password1") ||
                errors.contains("password2") ||
                errors.contains("country")) {
            return APIErrorType.SignUpError
        }
        return APIErrorType.General
    }

    private fun messageFrom(errors: HashMap<String, String>): String? {
        return errors.values.first()
    }

    private val errorMapType = object : TypeToken<HashMap<String, Any>>() {}.type
    // endregion
}

