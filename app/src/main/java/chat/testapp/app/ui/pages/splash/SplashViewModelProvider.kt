/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/23/18.
 */

package chat.testapp.app.ui.pages.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chat.testapp.ui.navigation.router.Router
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


internal class SplashViewModelProvider(
    private val kodein: Kodein,
    private val isShowContinue: Boolean
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            val router: Router by kodein.instance()

            val configurator = SplashViewModel.Configurator(router, isShowContinue)

            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(configurator) as T
        }
        throw IllegalArgumentException("View model $modelClass is not supported")
    }
}