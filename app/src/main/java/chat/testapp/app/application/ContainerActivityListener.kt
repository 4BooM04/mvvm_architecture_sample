/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/20/18.
 */

package chat.testapp.app.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import chat.testapp.ui.navigation.router.Router

internal class ContainerActivityListener: Application.ActivityLifecycleCallbacks {

    var router: Router? = null

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity is Router) {
            router = activity
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (activity is Router) {
            router = null
        }
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }
}
