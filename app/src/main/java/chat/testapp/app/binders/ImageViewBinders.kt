/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/29/18.
 */

package chat.testapp.app.binders

import androidx.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

internal class ImageViewBinders {


    companion object {

        @JvmStatic
        @BindingAdapter(value = ["path", "error", "placeholder"], requireAll = false)
        fun loadImage(view: ImageView, path: String?, error: Drawable?, placeholder: Drawable?) {
            val requestOptions = RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(view.context.applicationContext)
                .load(path)
                .thumbnail(0.3f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(view)
        }
    }
}