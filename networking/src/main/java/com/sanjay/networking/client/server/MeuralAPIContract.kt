package com.sanjay.networking.client.server

import com.sanjay.networking.annotations.OrientationString
import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.request.UserFeedback
import com.sanjay.networking.response.model.*
import com.sanjay.networking.result.APIResult
import okhttp3.MultipartBody

abstract class MeuralAPIContract {

    companion object {
        private const val MAX_PAGE_SIZE = 10
    }

    // region - Article
    abstract suspend fun getArticle(id: Long): APIResult<Article>

    abstract suspend fun getArticleItems(id: Long,
                                         page: Int? = 1,
                                         count: Int? = Int.MAX_VALUE,
                                         sort: Sort? = null,
                                         filter: Filter? = null): APIResult<List<Image>>

    abstract suspend fun getArticlesIn(model: String,
                                       id: Long,
                                       page: Int,
                                       count: Int = MAX_PAGE_SIZE,
                                       sort: Sort? = null,
                                       filter: Filter? = null): APIResult<List<Article>>
    // endregion

    // region - Artist
    abstract suspend fun getArtist(id: Long): APIResult<Artist>

    abstract suspend fun getArtists(page: Int? = 1,
                                    count: Int? = Int.MAX_VALUE,
                                    sort: Sort? = null,
                                    filter: Filter? = null): APIResult<List<Artist>>

    abstract suspend fun getArtistGalleries(id: Long,
                                            page: Int? = 1,
                                            count: Int? = Int.MAX_VALUE,
                                            sort: Sort? = null,
                                            filter: Filter? = null): APIResult<List<Gallery>>

    abstract suspend fun getArtistItems(id: Long,
                                        page: Int? = 1,
                                        count: Int? = Int.MAX_VALUE,
                                        sort: Sort? = null,
                                        filter: Filter? = null): APIResult<List<Image>>

    abstract suspend fun getArtistsIn(model: String, id: Long, page: Int, count: Int = MAX_PAGE_SIZE, sort: Sort? = null, filter: Filter? = null): APIResult<List<Artist>>
    // endregion

    // region - Auth
    abstract suspend fun login(email: String, password: String): APIResult<Token>

    abstract suspend fun register(firstName: String, lastName: String, email: String,
                                  password: String, confirmPassword: String, countryCode: String,
                                  isReceiveCommunications: Boolean, isSecurityToken: Boolean): APIResult<Token>
    // endregion

    // region - Category
    abstract suspend fun getCategory(id: Long): APIResult<Category>

    abstract suspend fun getCategoryArtists(id: Long,
                                            page: Int? = 1,
                                            count: Int? = Int.MAX_VALUE,
                                            sort: Sort? = null,
                                            filter: Filter? = null): APIResult<List<Artist>>

    abstract suspend fun getCategoryGalleries(id: Long,
                                              page: Int? = 1,
                                              count: Int? = Int.MAX_VALUE,
                                              sort: Sort? = null,
                                              filter: Filter? = null): APIResult<List<Gallery>>

    abstract suspend fun getCategoryItems(id: Long,
                                          page: Int? = 1,
                                          count: Int? = Int.MAX_VALUE,
                                          sort: Sort? = null,
                                          filter: Filter? = null): APIResult<List<Image>>
    // endregion

    // region - Channel
    abstract suspend fun getChannel(id: Long): APIResult<Channel>

    abstract suspend fun getChannels(page: Int? = 1,
                                     count: Int? = Int.MAX_VALUE,
                                     sort: Sort? = null,
                                     filter: Filter? = null): APIResult<List<Channel>>

    abstract suspend fun getChannelGalleries(id: Long,
                                             page: Int? = 1,
                                             count: Int? = Int.MAX_VALUE,
                                             sort: Sort? = null,
                                             filter: Filter? = null): APIResult<List<Gallery>>

    abstract suspend fun getChannelArtists(id: Long,
                                           page: Int? = 1,
                                           count: Int? = Int.MAX_VALUE,
                                           sort: Sort? = null,
                                           filter: Filter? = null): APIResult<List<Artist>>

    abstract suspend fun getChannelItems(id: Long,
                                         page: Int? = 1,
                                         count: Int? = Int.MAX_VALUE,
                                         sort: Sort? = null,
                                         filter: Filter? = null): APIResult<List<Image>>
    // endregion

    // region - Device
    abstract suspend fun getDevice(id: Long): APIResult<Device>

    abstract suspend fun getGalleriesFor(deviceId: Long): APIResult<List<Gallery>>
    abstract suspend fun deleteDevice(id: Long): APIResult<Unit>
    abstract suspend fun performCommand(id: Long, deviceCommand: DeviceCommand): APIResult<Unit>
    abstract suspend fun setSchedulerEnabled(deviceId: Long, schedulerEnabled: Boolean): APIResult<Device>
    abstract suspend fun resync(id: Long, timeout: Int = 90)
    abstract suspend fun addGalleryToCanvas(deviceId: Long, galleryId: Long): APIResult<Device>
    abstract suspend fun previewImageOnCanvas(deviceId: Long, imageId: Long): APIResult<Unit>
    abstract suspend fun getAllWorks(deviceId: Long,
                                     page: Int? = 1,
                                     count: Int? = Int.MAX_VALUE,
                                     sort: Sort? = null): APIResult<List<Image>>

    abstract suspend fun deleteGalleryFromCanvas(deviceId: Long, galleryId: Long): APIResult<Device>
    abstract suspend fun removeAllWorksItem(deviceId: Long, workId: Long): APIResult<Device>

    abstract suspend fun addAllWorksItem(deviceId: Long, itemId: Long): APIResult<Device>
    // endregion

    // region - Favorites
    abstract suspend fun favorite(model: String, id: Long, isFavorite: Boolean): APIResult<Boolean>

    abstract suspend fun getFavoriteArtists(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<Artist>>?

    abstract suspend fun getFavoriteCategories(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<Category>>?

    abstract suspend fun getFavoriteChannels(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<Channel>>?

    abstract suspend fun getFavoriteGalleries(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<Gallery>>?

    abstract suspend fun getFavoriteImages(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<Image>>?
    // endregion

    // region - Feed
    abstract suspend fun getFeed(page: Int = 1, count: Int = Int.MAX_VALUE): APIResult<List<FeedItem>>
    // endregion

    // region - Gallery
    abstract suspend fun getGallery(id: Long): APIResult<Gallery>

    abstract suspend fun getGalleriesIn(model: String, id: Long, page: Int, count: Int = MAX_PAGE_SIZE, sort: Sort? = null, filter: Filter? = null): APIResult<List<Gallery>>
    abstract suspend fun getGalleryItems(galleryId: Long, page: Int? = 1, count: Int? = Int.MAX_VALUE): APIResult<List<Image>>
    abstract suspend fun addItemToGallery(galleryId: Long, itemId: Long): APIResult<Gallery>
    abstract suspend fun removeItemFromGallery(galleryId: Long, itemId: Long): APIResult<Unit>
    abstract suspend fun deleteGallery(galleryId: Long): APIResult<Unit>
    abstract suspend fun sortGallery(galleryId: Long, order: List<Long>): APIResult<Gallery>

    abstract suspend fun createGallery(name: String, description: String, @OrientationString orientation: String?)
            : APIResult<Gallery>

    abstract suspend fun updateGallery(galleryId: Long, name: String, description: String,
                                       @OrientationString orientation: String?, coverId: Long?)
            : APIResult<Gallery>
    // endregion

    // region - Groups
    abstract suspend fun getGroups(): APIResult<List<Group>>

    abstract suspend fun getGroupCategories(id: Long): APIResult<List<Category>>

    // endregion

    // region - Item
    abstract suspend fun getItem(id: Long): APIResult<Image>

    abstract suspend fun getItemsIn(model: String, id: Long, page: Int, count: Int = MAX_PAGE_SIZE, sort: Sort? = null, filter: Filter? = null): APIResult<List<Image>>
    abstract suspend fun uploadItem(item: MultipartBody.Part): APIResult<Image>
    abstract suspend fun recropItem(itemId: Long, cropPoints: CropPoints): APIResult<Image>
    abstract suspend fun updateImageData(imageId: Long, title: String, author: String, description: String, medium: String? = null, year: String): APIResult<Image>
    abstract suspend fun deleteImage(imageId: Long): APIResult<Unit>

    // endregion

    // region - User
    abstract suspend fun getUserWithCanvases(): APIResult<UserWithCanvases>

    abstract suspend fun getUser(): APIResult<User>
    abstract suspend fun getUserCanvases(): APIResult<List<Device>>
    abstract suspend fun submitFeedback(userFeedback: UserFeedback): APIResult<Unit>
    abstract suspend fun getUserPurchasedWorks(): APIResult<List<Image>>
    abstract suspend fun getUserPurchasedGalleries(): APIResult<List<Gallery>>
    abstract suspend fun addUserCanvas(productKey: String): APIResult<Device>
    abstract suspend fun updateCanvas(deviceId: Long, newSettings: DeviceOptions): APIResult<Device>?
    abstract suspend fun getUserGalleries(page: Int = 1, count: Int = Int.MAX_VALUE, sort: Sort? = null)
            : APIResult<List<Gallery>>?

    abstract suspend fun getuserImages(page: Int): APIResult<List<Image>>?
    abstract suspend fun purchaseGallery(contentId: Long): APIResult<Purchase<Gallery>>
    abstract suspend fun purchaseImage(contentId: Long): APIResult<Purchase<Image>>
    abstract suspend fun getDeviceLanguages(): APIResult<List<Language>>

    // endregion

    // region - Search
    abstract suspend fun search(searchTerm: String, page: Int? = 1, count: Int? = MAX_PAGE_SIZE)
            : APIResult<SearchResults>
    // endregion
}