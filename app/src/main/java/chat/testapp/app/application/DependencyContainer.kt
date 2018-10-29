/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.application

import android.app.Application
import chat.testapp.core.systemServices.ActiveNetworkLiveData
import chat.testapp.ui.navigation.router.Router
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI
import org.kodein.di.Kodein
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal class DependencyContainer(configurator: Configurator) {

    class Configurator(
        val kodein: ConfigurableKodein,
        val application: Application,
        val router: Router
    )

    companion object {
        const val BG_CONTEXT_TAG = "bgContext"
        const val UI_CONTEST_TAG = "uiContext"
    }

    private val kodein = configurator.kodein
    private val application = configurator.application
    private val router = configurator.router


    var overrideModule: Kodein.Module? = null
        set(value) {
            resetInjection()
            value?.let {
                kodein.addImport(it, true)
            }
            field = value
        }

    fun addModule(activityModules: Kodein.Module) {
        kodein.addImport(activityModules, true)
    }

    fun resetInjection() {
        kodein.clear()
        addModule(coroutinesDependencies())
        addModule(navigationDependencies())
        addModule(systemServicesDependencies())

    }


    private fun coroutinesDependencies() = Kodein.Module("Coroutines module") {
        bind<CoroutineDispatcher>(UI_CONTEST_TAG) with provider { UI }
        bind<CoroutineDispatcher>(BG_CONTEXT_TAG) with provider { CommonPool }
    }

    private fun navigationDependencies() = Kodein.Module("Navigation module", true) {
        bind<Router>() with provider { router }
    }


    private fun systemServicesDependencies() = Kodein.Module("Storage module") {
        bind<ActiveNetworkLiveData>() with singleton { ActiveNetworkLiveData(application) }
    }
}

