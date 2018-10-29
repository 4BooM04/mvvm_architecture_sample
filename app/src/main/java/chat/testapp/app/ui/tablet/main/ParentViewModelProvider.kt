package chat.testapp.app.ui.tablet.main

import chat.testapp.app.ui.base.ContainerViewModel


interface ParentViewModelProvider {
    fun getParentViewModel(): ContainerViewModel
}
