/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.binders

import androidx.databinding.BindingAdapter
import android.view.View


internal class ViewBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("visibleOrGone")
        fun setVisibleOrGone(view: View, isShow: Boolean) {
            if (isShow) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("isClickable")
        fun isClickable(view: View, isClickable: Boolean) {
            view.isClickable = isClickable
        }
    }
}
