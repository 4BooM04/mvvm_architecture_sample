package chat.testapp.app.ui.pages.conversation


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import chat.testapp.app.R
import chat.testapp.app.databinding.FragmentConversationBinding
import chat.testapp.core.models.Id
import chat.testapp.ui.container.KeyboardDetector
import chat.testapp.ui.extentions.getStatusBarHeight
import kotlinx.android.synthetic.main.fragment_conversation.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class ConversationFragment : Fragment(), KodeinAware {
    companion object {

        private val CONVERSATION_ID = "conversation_id"
        private val IS_SHOW_NAVIGATION_BUTTON = "is_show_navigation_button"

        fun newInstance(conversationId: Id, isShowNavigationButton: Boolean = true) =
                ConversationFragment().apply {
                    arguments = bundleOf(CONVERSATION_ID to conversationId,
                            IS_SHOW_NAVIGATION_BUTTON to isShowNavigationButton)
                }
    }

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: FragmentConversationBinding
    private lateinit var keyboardDetector: KeyboardDetector
    private val conversationId by lazy {
        arguments?.getString(CONVERSATION_ID) ?: throw IllegalStateException()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is KeyboardDetector) {
            keyboardDetector = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConversationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        bindView()
        val topPadding = activity?.getStatusBarHeight()?:0
        toolbar.setPadding(0,topPadding,0,0)
    }

    private fun bindView() {
        val isShowNavigationButton = arguments?.getBoolean(IS_SHOW_NAVIGATION_BUTTON) ?: true
        val viewModel = prepareViewModel(isShowNavigationButton)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }

    private fun prepareViewModel(isShowNavigationButton: Boolean): ConversationViewModel {
        val provider = ConversationViewModelProvider(kodein, conversationId, isShowNavigationButton)
        return ViewModelProviders.of(this, provider).get(ConversationViewModel::class.java)
    }

}
