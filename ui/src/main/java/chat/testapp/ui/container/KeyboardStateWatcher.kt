/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/30/18.
 */

package chat.testapp.ui.container

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver


class KeyboardStateWatcher(private val activityRootView: View) :
    ViewTreeObserver.OnGlobalLayoutListener, KeyboardDetector {

    private val keyboardListeners = mutableSetOf<KeyboardListener>()
    private lateinit var lastState: KeyboardState

    override fun onGlobalLayout() {
        val state = if (isKeyboardOpen()) KeyboardState.OPEN else KeyboardState.CLOSED

        if (::lastState.isInitialized && lastState != state || !::lastState.isInitialized) {
            keyboardListeners.forEach { it.onKeyboardStateChanged(state) }
            lastState = state
        }
    }

    override fun isKeyboardOpen(): Boolean {
        val r = Rect()
        activityRootView.getWindowVisibleDisplayFrame(r)
        return activityRootView.rootView.height - (r.bottom - r.top) > 200
    }

    override fun addOnKeyboardListener(keyboardListener: KeyboardListener) {
        keyboardListeners.add(keyboardListener)
    }

    override fun removeOnKeyboardListener(keyboardListener: KeyboardListener) {
        keyboardListeners.remove(keyboardListener)
    }
}