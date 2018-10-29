/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/24/18.
 */

package chat.testapp.core.systemServices

import android.app.Application
import androidx.lifecycle.LiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import chat.testapp.core.systemServices.ActiveNetworkLiveData.ActiveNetwork

class ActiveNetworkLiveData(val appContext: Application) : LiveData<ActiveNetwork>() {
    private var activeNetwork: ActiveNetwork? = null

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val network = appContext.getActiveNetwork()

            if (activeNetwork != network) {
                activeNetwork = network
                postValue(network)
            }
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        appContext.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        appContext.unregisterReceiver(networkReceiver)
    }

    sealed class ActiveNetwork {
        object Wifi : ActiveNetwork()
        object Mobile : ActiveNetwork()
        object NotFound : ActiveNetwork()
    }
}
internal fun Context.getActiveNetwork(): ActiveNetwork {
    (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let {
        it.activeNetworkInfo?.let {
            return if (it.isConnectedOrConnecting) {
                when (it.type) {
                    ConnectivityManager.TYPE_WIFI -> ActiveNetwork.Wifi
                    ConnectivityManager.TYPE_MOBILE -> ActiveNetwork.Mobile
                    else -> ActiveNetwork.NotFound
                }
            } else {
                ActiveNetwork.NotFound
            }
        }
    }
    return ActiveNetwork.NotFound
}