/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/23/18.
 */

package chat.testapp.app.ui.pages.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chat.testapp.ui.navigation.router.Router
import chat.testapp.ui.utils.ActionLiveData
import chat.testapp.app.ui.base.Event

internal class SplashViewModel(configurator: Configurator): ViewModel() {

    class Configurator(
            val router: Router,
            val isShowContinue: Boolean
    )


    private val router = configurator.router

    val isShowContinue = MutableLiveData<Boolean>()
    val isContinueClickable = MutableLiveData<Boolean>()
    val onContinueClicked = ActionLiveData<Void>()

    init {
        isContinueClickable.value = true
        this.isShowContinue.value = configurator.isShowContinue
        onContinueClicked.observeForever { router.route(Event.OnLeaveSplash()) }
    }

    fun continueClicked() {
        isContinueClickable.value = false
        onContinueClicked.call()
    }

}