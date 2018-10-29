/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/23/18.
 */

package chat.testapp.ui.extentions

/**
 * @throws [IllegalArgumentException] when type [defaultValue] is not supported
 */
@Suppress("UNCHECKED_CAST")
fun <T> androidx.fragment.app.Fragment.getArgument(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is String? -> arguments?.getString(key) as T ?: defaultValue
        is Int? -> arguments?.getInt(key) as T ?: defaultValue
        is Boolean -> arguments?.getBoolean(key) as T ?: defaultValue

        else -> {
            val valueType = defaultValue?.let { (defaultValue as Any)::class.java }
            val message = "Type of defaultValue $valueType is not supported"

            throw  IllegalArgumentException(message)
        }
    }
}
