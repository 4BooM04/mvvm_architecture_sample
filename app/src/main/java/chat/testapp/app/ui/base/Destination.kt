package chat.testapp.app.ui.base

import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.dispatcher.IDestination
import chat.testapp.ui.navigation.dispatcher.StackFlag

/**
 * This class describe all possible destinations that can be achieved in application
 **/
internal sealed class Destination(flag: StackFlag = StackFlag.NORMAL) : IDestination(flag) {
    class Splash(val isShowContinue: Boolean) : Destination(StackFlag.CLEAR_TOP)
    class Registration : Destination(StackFlag.CLEAR_TOP)
    class Workspace(val workspaceId: Id) : Destination(StackFlag.CLEAR_TOP)
    class Channels(val workspaceId: Id) : Destination()
    class People(val workspaceId: Id) : Destination()
    class Conversation(val conversationId: Id) : Destination(StackFlag.SINGLE_TOP)
    class Back : Destination()
}