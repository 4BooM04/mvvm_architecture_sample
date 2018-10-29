/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 8/3/18.
 */

package chat.testapp.app.ui.pages.workspace

import androidx.lifecycle.ViewModel
import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.router.Router
import chat.testapp.app.ui.base.Event

internal class WorkspaceViewModel(configurator: Configurator) : ViewModel() {

    class Configurator(
        val workspaceId: Id,
        val router: Router
    )

    private val workspaceId = configurator.workspaceId
    private val router: Router = configurator.router

    fun onConversationClick(/*conversation: Id*/) {
        router.route(Event.OnShowConversation(""))
    }

}