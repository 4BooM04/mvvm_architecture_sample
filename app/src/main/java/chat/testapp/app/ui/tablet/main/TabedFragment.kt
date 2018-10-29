package chat.testapp.app.ui.tablet.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import chat.testapp.app.R
import chat.testapp.app.ui.base.Destination
import chat.testapp.app.ui.pages.conversation.ConversationFragment
import chat.testapp.app.ui.pages.workspace.WorkspaceFragment
import chat.testapp.core.models.Id
import chat.testapp.ui.navigation.dispatcher.Dispatcher
import chat.testapp.ui.navigation.dispatcher.IDestination


class TabedFragment : androidx.fragment.app.Fragment(), Dispatcher {

    companion object {
        private val WORKSPACE_ID_KEY = "workspace_id_key"

        fun newInstance(workspaceId: Id) = TabedFragment().apply {
            arguments = bundleOf(WORKSPACE_ID_KEY to workspaceId)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tabbed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(WORKSPACE_ID_KEY) ?: ""
        showWorkspace(Destination.Workspace(id))
    }

    override fun dispatch(destination: IDestination) =
            when (destination) {
                is Destination.Conversation -> showConversation(destination)
                else -> {
                }
            }

    override fun onDetach() {
        super.onDetach()
    }

    private fun showWorkspace(workspace: Destination.Workspace) {
        val workspaceFragment = WorkspaceFragment.newInstance(workspace.workspaceId)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.navigationContainer, workspaceFragment)
        }.commit()
    }


    private fun showConversation(conversation: Destination.Conversation) {
        val conversationFragment = ConversationFragment.newInstance(conversation.conversationId, false)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.pageContainer, conversationFragment, "showConversation")
        }.commit()
    }


}
