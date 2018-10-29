/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/20/18.
 */

package chat.testapp.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chat.testapp.app.application.DependencyContainer
import chat.testapp.core.systemServices.ActiveNetworkLiveData

import kotlinx.coroutines.experimental.CoroutineDispatcher
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

internal class ContainerViewModelProvider(
    private val kodein: Kodein
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ContainerViewModel::class.java)) {
            val activeNetworkLiveData: LiveData<ActiveNetworkLiveData.ActiveNetwork> by kodein.instance()
            val bgContext: CoroutineDispatcher by kodein.instance(tag = DependencyContainer.BG_CONTEXT_TAG)

            val configurator = ContainerViewModel.Configurator(
                    bgContext,
                    activeNetworkLiveData)

            @Suppress("UNCHECKED_CAST")
            return ContainerViewModel(configurator) as T
        }

        throw IllegalArgumentException("View model $modelClass is not supported")
    }
}