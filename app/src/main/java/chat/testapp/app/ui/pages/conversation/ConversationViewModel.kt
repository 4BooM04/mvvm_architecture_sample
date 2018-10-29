/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.ui.pages.conversation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.router.Router
import chat.testapp.app.ui.base.Event
import kotlinx.coroutines.experimental.*


internal class ConversationViewModel(configurator: Configurator) : ViewModel() {

    class Configurator(
            val conversationId: Id,
            val isShowNavigationButton: Boolean,
            val bgContext: CoroutineDispatcher,
            val router: Router
    )

    private val bgContext = configurator.bgContext
    private val conversationId = configurator.conversationId
    private val router: Router = configurator.router
    val navigationIconVisibility = MutableLiveData<Boolean>()

    init {
        navigationIconVisibility.postValue( configurator.isShowNavigationButton)
    }

    fun onBackPressed() {
        router.route(Event.OnBack())
    }

}