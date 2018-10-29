package chat.testapp.ui.navigation.dispatcher

/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/19/18.
 */

import android.util.Log
import chat.testapp.ui.navigation.dispatcher.StackFlag.*

/**
 * Dispatcher - an interface that handles page change action from
 * view model and process page changing (replace fragment in this case)
 * Dispatcher is also needed support for DestinationStack
 * component to recreate pages structure during activity recreation.
 * This feature need in case you handle different UI structure for
 * horizontal and vertical orientations and no need to retain fragments
 * during orientation change action
 **/
interface Dispatcher {

    fun dispatch(destination: IDestination)

}

/**
 * abstract class that generify children destination classes
 * **/
abstract class IDestination(val flag: StackFlag = NORMAL)

enum class StackFlag {
    CLEAR_TOP, //remove all previous added events this to the top
    SINGLE_TOP, //remove all this type previously added events and ad this to the top
    NORMAL, //just add to the top of the stack
}

/**
 * Stack of destinations to handle activity recreations
 * **/
class DestinationStack {
    private val destinations: MutableList<IDestination> = mutableListOf()
    val isEmpty: Boolean get () = destinations.isEmpty()
    val allDestinations: List<IDestination> get() = destinations.toList()

    fun pushDestination(destination: IDestination) {
        val address = destination
        when (destination.flag) {
            CLEAR_TOP -> {
                destinations.clear()
                destinations.add(destination)
            }
            SINGLE_TOP -> {
                destinations.removeAll { destination::class.java.isAssignableFrom(it::class.java) }
                destinations.add(destination)
            }
            NORMAL -> {
                destinations.add(destination)
            }
        }
        Log.d("DestinationStack", destinations.size.toString())
    }

    fun dropToFist() {
        if (destinations.size > 0) {
            val firstItem = destinations[0]
            destinations.clear()
            destinations.add(firstItem)
        }
    }

    fun popDestination() {
        if (destinations.size > 0) {
            destinations.removeAt(destinations.lastIndex)
        }
    }

    val backStackCount: Int = destinations.size
}


