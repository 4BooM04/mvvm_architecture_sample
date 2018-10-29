/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 8/6/18.
 */

package chat.testapp.ui.extentions

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import android.widget.ImageView
import chat.testapp.ui.R
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setAvatar(avatarPath: String?, text: String?, @ColorInt textColor: Int) {
    val placeholder = if (text?.isNotEmpty() != null) {
        val  textDrawable = TextDrawable
            .builder()
            .beginConfig()
            .fontSize(getPxFromDp(24f).toInt())
            .textColor(textColor)
            .endConfig()
            .buildRect(getFirstSymbols(text.toUpperCase()), Color.TRANSPARENT)

        val background = ContextCompat.getDrawable(context, R.drawable.bg_avatar_placeholder)
        val generator = ColorGenerator.MATERIAL
        background?.setColorFilter(generator.getColor(text), PorterDuff.Mode.SRC_IN)
        LayerDrawable(arrayOf(background, textDrawable))

    } else {
        ContextCompat.getDrawable(context, R.drawable.bg_avatar_placeholder)
    }

    Glide.with(this)
        .load(avatarPath)
        .apply(RequestOptions().placeholder(placeholder).circleCrop())
        .into(this)
}


private fun getFirstSymbols(text: String): String {
    val parts = text.split(" ")
    val stringBuilder = StringBuilder()
    val map = parts.mapNotNull { it.firstOrNull() }
    val first = map.firstOrNull()
    first?.let {
        stringBuilder.append(it)
    }
    if (map.size > 1) {
        val last = map.lastOrNull()
        last?.let {
            stringBuilder.append(it)
        }
    }

    return stringBuilder.toString()
}