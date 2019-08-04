package com.freight.shipper


import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.core.persistence.network.client.server.MeuralAPI
import com.freight.shipper.core.persistence.network.client.server.MeuralAPIFactory

class FreightApplication : Application() {

    //region -Properties
//    val assetsManager: AssetsManager by lazy { AssetsManager(this) }
//    val prefManager: PrefManager by lazy { PrefManager(this) }
    val loginManager: LoginManager by lazy { LoginManager(this) }
    val meuralAPI: MeuralAPI by lazy { MeuralAPIFactory.standardClient(loginManager) }
    //endregion

    init {
        instance = this
    }

    //region -Companion function
    companion object {
        lateinit var instance: FreightApplication
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
