/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 7/4/18.
 */

package chat.testapp.ui.extentions

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintLayout.setMatchConstraintToParent(viewId: Int) {
    val set = ConstraintSet()
    set.clone(this)

    set.connect(
        viewId, ConstraintSet.TOP,
        ConstraintSet.PARENT_ID, ConstraintSet.TOP
    )
    set.connect(
        viewId, ConstraintSet.BOTTOM,
        ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
    )
    set.connect(
        viewId, ConstraintSet.START,
        ConstraintSet.PARENT_ID, ConstraintSet.START
    )
    set.connect(
        viewId, ConstraintSet.END,
        ConstraintSet.PARENT_ID, ConstraintSet.END
    )
    set.constrainWidth(viewId, ConstraintSet.MATCH_CONSTRAINT)
    set.constrainHeight(viewId, ConstraintSet.MATCH_CONSTRAINT)

    set.applyTo(this)
}