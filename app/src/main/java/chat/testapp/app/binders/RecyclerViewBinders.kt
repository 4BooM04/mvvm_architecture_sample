/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.binders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


internal class RecyclerViewBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
            view.adapter = adapter
        }

        @JvmStatic
        @BindingAdapter("stackFromEnd")
        fun setStackFromEnd(view: androidx.recyclerview.widget.RecyclerView, isStackFromEnd: Boolean) {
            (view.layoutManager as? androidx.recyclerview.widget.LinearLayoutManager)?.stackFromEnd = isStackFromEnd
        }
    }
}