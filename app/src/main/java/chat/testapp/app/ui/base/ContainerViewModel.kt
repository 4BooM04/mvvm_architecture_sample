/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/19/18.
 */

package chat.testapp.app.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import android.widget.RadioGroup
import chat.testapp.core.systemServices.ActiveNetworkLiveData
import chat.testapp.ui.navigation.dispatcher.DestinationStack
import chat.testapp.ui.navigation.dispatcher.IDestination
import chat.testapp.ui.navigation.router.IEvent
import chat.testapp.ui.navigation.router.Router
import chat.testapp.ui.utils.ActionLiveData
import chat.testapp.app.ui.base.Event
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.delay


class ContainerViewModel(val configurator: Configurator) : ViewModel(), Router {

    class Configurator(
            val bgContext: CoroutineDispatcher,
            val activeNetworkLiveData: LiveData<ActiveNetworkLiveData.ActiveNetwork> )

    val activeNetworkLiveData: LiveData<ActiveNetworkLiveData.ActiveNetwork> = configurator.activeNetworkLiveData
    val changePageEvent = ActionLiveData<IDestination>()
    val eventStackRecreationAction = ActionLiveData<List<IDestination>>()

    private val stack = DestinationStack()
    private val bgContext: CoroutineDispatcher = configurator.bgContext

    private val routerActor = actor<IEvent>(bgContext) {
        for (event in channel) {
            when (event) {
                is Event.OnShowSplash -> handleShowSplashEvent(event)
                is Event.OnLeaveSplash -> changePageEvent.postValue(Destination.Registration())
                is Event.OnShowWorkspace -> handleShowWorkspaceEvent(event)
                is Event.OnShowChannels -> handleShowChannelsEvent(event)
                is Event.OnShowPeople -> handleShowPeopleEvent(event)
                is Event.OnShowConversation -> handleShowConversationEvent(event)
                is Event.OnBack -> onBackStack()
            }
        }
    }

    fun attach(recreation: Boolean) {
        if (!recreation) {
            route(Event.OnShowSplash())
        } else if (!stack.isEmpty) {
            eventStackRecreationAction.postValue(stack.allDestinations)
        } else {
            route(Event.OnShowSplash())
        }
    }

    override fun route(event: IEvent) = routerActor.offer(event)

    private suspend fun handleShowSplashEvent(event: Event.OnShowSplash) {
        sendEventToStack(Destination.Splash(false))
        delay(3000)
        sendEventToStack(Destination.Workspace("workspace.test"))
        // sendEventToStack(Destination.Registration())
    }

    private suspend fun handleShowWorkspaceEvent(event: Event.OnShowWorkspace) {
        sendEventToStack(Destination.Workspace(event.workspaceId))
    }

    private suspend fun handleShowConversationEvent(event: Event.OnShowConversation) {
        sendEventToStack(Destination.Conversation(event.conversationId))
    }

    private suspend fun handleShowChannelsEvent(event: Event.OnShowChannels) {
        sendEventToStack(Destination.Channels(event.workspaceId))
    }

    private suspend fun handleShowPeopleEvent(event: Event.OnShowPeople) {
        sendEventToStack(Destination.People(event.workspaceId))
    }

    private fun sendEventToStack(destination: IDestination) {
        stack.pushDestination(destination as Destination)
        changePageEvent.postValue(destination)
    }

    private fun onBackStack() {
        stack.popDestination()
        changePageEvent.postValue(Destination.Back())
    }

    override fun onCleared() {
        super.onCleared()
    }
}