package com.freight.shipper


import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory

class CropApplication : Application() {

    //region -Properties
//    val loginManager: LoginManager by lazy { LoginManager(this) }
//    val assetsManager: AssetsManager by lazy { AssetsManager(this) }
//    val prefManager: PrefManager by lazy { PrefManager(this) }
//    val meuralAPI: MeuralAPI by lazy { MeuralAPIFactory.standardClient(loginManager) }
    //endregion

    init {
        instance = this
    }

    //region -Companion function
    companion object {
        lateinit var instance: CropApplication
    }
    //endregion

    //region -Lifecycle functions
    override fun onCreate() {
        super.onCreate()
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)
    }
    //endregion

    //region - Private functions
    //endregion

}
