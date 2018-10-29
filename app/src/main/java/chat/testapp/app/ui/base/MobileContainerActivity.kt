/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.FloatRange
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import chat.testapp.app.R
import chat.testapp.app.application.DependencyContainer
import chat.testapp.app.databinding.ActivityContainerBinding
import chat.testapp.app.ui.pages.conversation.ConversationFragment
import chat.testapp.app.ui.pages.splash.SplashFragment
import chat.testapp.app.ui.pages.workspace.WorkspaceFragment
import chat.testapp.ui.container.KeyboardDetector
import chat.testapp.ui.container.KeyboardListener
import chat.testapp.ui.container.KeyboardStateWatcher
import chat.testapp.ui.extentions.hideSoftKeyboard
import chat.testapp.ui.navigation.dispatcher.Dispatcher
import chat.testapp.ui.navigation.dispatcher.IDestination
import chat.testapp.ui.navigation.router.IEvent
import chat.testapp.ui.navigation.router.Router
import chat.testapp.app.ui.base.Event
import kotlinx.android.synthetic.main.activity_container.*
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


internal class MobileContainerActivity :
        AppCompatActivity(),
        KodeinAware,
        Router,
        Dispatcher,
        KeyboardDetector {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MobileContainerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val kodein: Kodein by closestKodein()
    private lateinit var binding: ActivityContainerBinding
    private lateinit var keyboardStateWatcher: KeyboardStateWatcher

    private val isHomeAsUp
        get() = supportFragmentManager.backStackEntryCount > 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_container)
        binding.setLifecycleOwner(this)

        val viewModel = prepareViewModel()
        binding.viewModel = viewModel

        if (savedInstanceState == null) {
            // TODO Sometimes route(Event.OnShowSplash()) return false. This allow fix that. But need find better design.
            val bgContext: CoroutineDispatcher by kodein.instance(DependencyContainer.BG_CONTEXT_TAG)
            launch (bgContext) {
                while(!route(Event.OnShowSplash())) {
                    delay(50)
                }
            }
        }
        keyboardStateWatcher = KeyboardStateWatcher(pageContainer)

    }

    override fun route(event: IEvent) = binding.viewModel?.route(event) ?: false

    override fun dispatch(destination: IDestination) {
        hideSoftKeyboard()
        when (destination) {
            is Destination.Splash -> showSplashPage(destination)
            is Destination.Registration -> showRegistration(destination)
            is Destination.Workspace -> showWorkspace(destination)
            is Destination.Conversation -> showConversation(destination)
            is Destination.Back -> showPreviousPage(destination)
            else -> {
            }
        }
    }

    override fun onBackPressed() {
        if (isKeyboardOpen()) {
            hideSoftKeyboard()
        } else {
            route(Event.OnBack())
        }
    }

    override fun isKeyboardOpen() = keyboardStateWatcher.isKeyboardOpen()

    override fun addOnKeyboardListener(keyboardListener: KeyboardListener) {
        keyboardStateWatcher.addOnKeyboardListener(keyboardListener)
    }

    override fun removeOnKeyboardListener(keyboardListener: KeyboardListener) {
        keyboardStateWatcher.removeOnKeyboardListener(keyboardListener)
    }

    private fun prepareViewModel(): ContainerViewModel {
        val provider = ContainerViewModelProvider(kodein)

        return ViewModelProviders.of(this, provider).get(ContainerViewModel::class.java).also {
            it.changePageEvent.observe(this, Observer { it?.let { dispatch(it) } })
            it.activeNetworkLiveData.observe(this, Observer {
                it?.let {
                    Toast.makeText(this, it::class.java.simpleName, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showPreviousPage(previousPage: Destination.Back) {
        super.onBackPressed()
    }

    private fun showSplashPage(splash: Destination.Splash) {
        val newInstance = SplashFragment.newInstance(splash.isShowContinue)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.pageContainer, newInstance)
        }.commit()

    }

    private fun showRegistration(registration: Destination.Registration) {

    }

    private fun showWorkspace(workspace: Destination.Workspace) {
        val workspaceFragment = WorkspaceFragment.newInstance(workspace.workspaceId)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.pageContainer, workspaceFragment)
        }.commit()
    }


    private fun showConversation(conversation: Destination.Conversation) {
        val conversationFragment = ConversationFragment.newInstance(conversation.conversationId)
        supportFragmentManager.beginTransaction().apply {
            addToBackStack("showConversation")
            replace(R.id.pageContainer, conversationFragment, "showConversation")
        }.commit()
    }


}
