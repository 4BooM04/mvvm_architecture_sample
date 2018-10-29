package chat.testapp.app.ui.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import chat.testapp.app.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isTabletMode()) {
            TabletContainerActivity.start(this)
        } else {
            MobileContainerActivity.start(this)
        }
        finish()
    }
}

fun Context.isTabletMode(): Boolean {
    val screenSize = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return when (screenSize) {
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> true
        Configuration.SCREENLAYOUT_SIZE_LARGE -> true
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> false
        Configuration.SCREENLAYOUT_SIZE_SMALL -> false
        else -> false
    }
}