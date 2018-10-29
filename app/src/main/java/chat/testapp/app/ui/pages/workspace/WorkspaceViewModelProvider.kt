/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 8/3/18.
 */

package chat.testapp.app.ui.pages.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.router.Router
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

internal class WorkspaceViewModelProvider(private val kodein: Kodein, val workspaceId: Id) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceViewModel::class.java)) {
            val router: Router by kodein.instance()


            val configurator = WorkspaceViewModel.Configurator(
                workspaceId,
                router
            )

            @Suppress("UNCHECKED_CAST")
            return WorkspaceViewModel(configurator) as T
        }

        throw IllegalArgumentException("View model $modelClass is not supported")
    }
}