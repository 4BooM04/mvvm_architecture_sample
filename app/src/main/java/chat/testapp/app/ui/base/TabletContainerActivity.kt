/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.ui.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import chat.testapp.app.R
import chat.testapp.app.application.DependencyContainer
import chat.testapp.app.databinding.ActivityContainerBinding
import chat.testapp.app.databinding.ActivityContainerBindingImpl
import chat.testapp.app.ui.pages.conversation.ConversationFragment

import chat.testapp.ui.container.KeyboardDetector
import chat.testapp.ui.container.KeyboardListener
import chat.testapp.ui.container.KeyboardStateWatcher

import chat.testapp.app.ui.pages.splash.SplashFragment
import chat.testapp.app.ui.pages.workspace.WorkspaceFragment
import chat.testapp.app.ui.tablet.main.ParentViewModelProvider
import chat.testapp.app.ui.tablet.main.TabedFragment
import chat.testapp.ui.extentions.hideSoftKeyboard
import chat.testapp.ui.navigation.dispatcher.Dispatcher
import chat.testapp.ui.navigation.dispatcher.IDestination
import chat.testapp.ui.navigation.router.IEvent
import chat.testapp.ui.navigation.router.Router
import chat.testapp.app.ui.base.Event
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class TabletContainerActivity :
        AppCompatActivity(),
        KodeinAware,
        Router,
        Dispatcher,
        ParentViewModelProvider,
        KeyboardDetector {


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TabletContainerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: ActivityContainerBinding
    private lateinit var keyboardStateWatcher: KeyboardStateWatcher
    val isLandscapeOrientation: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    lateinit var viewModel: ContainerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityContainerBinding>(this, R.layout.activity_container)
        binding.setLifecycleOwner(this)

        viewModel = prepareViewModel()
        binding.viewModel = viewModel

        if (savedInstanceState == null) {
            // TODO Sometimes route(Event.OnShowSplash()) return false. This allow fix that. But need find better design.
            val bgContext: CoroutineDispatcher by kodein.instance(DependencyContainer.BG_CONTEXT_TAG)
            launch(bgContext) {
                while (!route(Event.OnShowSplash())) {
                    delay(50)
                }
            }
        }

        viewModel.eventStackRecreationAction.observe(this@TabletContainerActivity, Observer {
            it?.let {
                for (i in it.iterator()) {
                    dispatch(i)
                }
            }
        })
        resetStack()
        val recreation = savedInstanceState != null
        viewModel.attach(recreation)
    }

    override fun getParentViewModel(): ContainerViewModel {
        return viewModel
    }

    override fun onResume() {
        super.onResume()
    }

    override fun route(event: IEvent) = binding.viewModel?.route(event) ?: false

    override fun dispatch(destination: IDestination) {
        hideSoftKeyboard()// i think this should be implemented inside each fragment
        when (destination) {
            is Destination.Splash -> showSplashPage(destination)
            is Destination.Registration -> showRegistration(destination)
            is Destination.Workspace -> showWorkspace(destination)
            is Destination.Back -> super.onBackPressed()
            else -> {
                dispatchSecondaryDestination(destination)
            }
        }
    }

    private fun dispatchSecondaryDestination(destination: IDestination) {
        val navigationFragment = supportFragmentManager.fragments.lastOrNull()
        when {
            destination is Destination.Back -> showPreviousPage(destination)
            navigationFragment is Dispatcher -> navigationFragment.dispatch(destination)
            else -> when (destination) {
                is Destination.Splash -> showSplashPage(destination)
                is Destination.Registration -> showRegistration(destination)
                is Destination.Workspace -> showWorkspace(destination)
                is Destination.Conversation -> showConversation(destination)

            }
        }
    }

    override fun onBackPressed() {
        route(Event.OnBack())
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




    private fun resetStack() {
        //Here we are removing all the fragment that are shown here
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    private fun showPreviousPage(previousPage: Destination.Back) {
        super.onBackPressed()
    }

    private fun showSplashPage(splash: Destination.Splash) {
        val newInstance = SplashFragment.newInstance(splash.isShowContinue)
        newInstance.retainInstance = false
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.pageContainer, newInstance)
        }.commitNow()
    }

    private fun showRegistration(registration: Destination.Registration) {

    }

    private fun showWorkspace(workspace: Destination.Workspace) {
        val workspaceFragment = if (isLandscapeOrientation) TabedFragment.newInstance(workspaceId = workspace.workspaceId) else WorkspaceFragment.newInstance(workspace.workspaceId)
        workspaceFragment.retainInstance = false
        supportFragmentManager.beginTransaction().apply {
            setPrimaryNavigationFragment(workspaceFragment)
            replace(R.id.pageContainer, workspaceFragment)
        }.commitNow()
    }


    private fun showConversation(conversation: Destination.Conversation) {
        val conversationFragment = ConversationFragment.newInstance(conversation.conversationId)
        conversationFragment.retainInstance = false
        supportFragmentManager.beginTransaction().apply {
            addToBackStack("showConversation")
            supportFragmentManager.findFragmentByTag("showConversation")?.let {
                remove(it)
            }
            replace(R.id.pageContainer, conversationFragment, "showConversation")
        }.commit()
    }


}
