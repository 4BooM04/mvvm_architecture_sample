/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.application

import android.app.Application
import android.content.Context
import chat.testapp.ui.navigation.router.IEvent
import chat.testapp.ui.navigation.router.Router
import com.chibatching.kotpref.Kotpref
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import java.lang.IllegalStateException

internal class AppInstance : Application(), KodeinAware, Router {

    override val kodein = ConfigurableKodein(mutable = true)

    private lateinit var dependencyContainer: DependencyContainer
    private var routerListener = ContainerActivityListener()

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)

        val configurator = DependencyContainer.Configurator(kodein, this, this)
        dependencyContainer = DependencyContainer(configurator)
        dependencyContainer.resetInjection()

        registerActivityLifecycleCallbacks(routerListener)
    }

    override fun route(event: IEvent) =
            routerListener.router?.route(event) ?: throw IllegalStateException()

}

internal fun Context.asApp() = (this as AppInstance)