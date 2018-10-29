package chat.testapp.app.ui.base

import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.router.IEvent

/**
 * This class describe all events from views that can cause navigation change
 **/
sealed class Event : IEvent {
   class OnShowSplash : Event()
   class OnLeaveSplash : Event()
   class OnShowWorkspace(val workspaceId: Id) : Event()
   class OnShowChannels(val workspaceId: Id) : Event()
   class OnShowPeople(val workspaceId: Id) : Event()
   class OnShowConversation(val conversationId: Id) : Event()
   class OnBack() : Event()
}