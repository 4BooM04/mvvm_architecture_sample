/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/24/18.
 */

package chat.testapp.ui.extentions

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import android.util.TypedValue

@ColorRes
fun Context.getColorResIdFromTheme(attrId: Int, @ColorRes defaultColor: Int = android.R.color.black): Int {
    val colorAttr = TypedValue()
    return if (theme.resolveAttribute(attrId, colorAttr, true)) {
        colorAttr.resourceId
    } else {
        defaultColor
    }
}

@ColorInt
fun Context.getColorFromTheme(attrId: Int, @ColorRes defaultColor: Int = android.R.color.black) =
    ContextCompat.getColor(this, getColorResIdFromTheme(attrId, defaultColor))