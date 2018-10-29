/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.ui.pages.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import chat.testapp.app.application.DependencyContainer
import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.router.Router
import kotlinx.coroutines.experimental.CoroutineDispatcher
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

internal class ConversationViewModelProvider(
    private val kodein: Kodein,
    private val conversationId: Id,
    private val isShowNavigationButton: Boolean
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            val bgContext: CoroutineDispatcher  by kodein.instance(DependencyContainer.BG_CONTEXT_TAG)
            val router: Router by kodein.instance()

            val configurator = ConversationViewModel.Configurator(
                conversationId,
                isShowNavigationButton,
                bgContext,
                router
            )

            @Suppress("UNCHECKED_CAST")
            return ConversationViewModel(configurator) as T
        }
        throw IllegalArgumentException("View model $modelClass is not supported")
    }
}
