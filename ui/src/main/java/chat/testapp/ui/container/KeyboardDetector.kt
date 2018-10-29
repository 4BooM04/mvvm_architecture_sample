/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/30/18.
 */

package chat.testapp.ui.container


 enum class KeyboardState {
    OPEN,
    CLOSED
}

 interface KeyboardListener {

    fun onKeyboardStateChanged(keyboardState: KeyboardState)
}

interface KeyboardDetector {

    fun isKeyboardOpen(): Boolean

    fun addOnKeyboardListener(keyboardListener: KeyboardListener)

    fun removeOnKeyboardListener(keyboardListener: KeyboardListener)
}

