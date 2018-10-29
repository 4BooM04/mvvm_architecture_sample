/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/19/18.
 */

package chat.testapp.ui.navigation.router
/**
 * Router - an interface that make the ability to send events from page view model to
 * global coordinator about the event that can change the page
 **/
interface Router {
    /**
     * @return true if [event] began to be processed, otherwise false
     */
    fun route(event: IEvent): Boolean

}

interface IEvent {}

