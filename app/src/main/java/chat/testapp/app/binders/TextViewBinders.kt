/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/19/18.
 */

package chat.testapp.app.binders

import androidx.databinding.BindingAdapter
import android.text.format.DateFormat
import android.widget.TextView
import chat.testapp.ui.formatter.DateTimeFormatter
import org.joda.time.DateTime
import org.joda.time.DateTimeComparator


class TextViewBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("formattedTime")
        fun setFormattedTime(view: TextView, dateTime: DateTime?) {
            view.text = dateTime?.let {
                if (DateFormat.is24HourFormat(view.context)) {
                    DateTimeFormatter().formatTimeFor24HourFormat(dateTime)
                } else {
                    DateTimeFormatter().formatTimeFor12HourFormat(dateTime)
                }
            } ?: let {
                ""
            }
        }

        /**
         * Format as time if [dateTime] is current date,
         * as day of week if [dateTime] less or equal 3 day,
         * otherwise as month with day
         */
        @JvmStatic
        @BindingAdapter("formattedDateOrTime")
        fun setFormattedDateOrTime(view: TextView, dateTime: DateTime?) {
            view.text = dateTime?.let {
                if (DateTimeComparator.getDateOnlyInstance().compare(it, DateTime.now()) == 0) {
                    if (DateFormat.is24HourFormat(view.context)) {
                        DateTimeFormatter().formatTimeFor24HourFormat(dateTime)
                    } else {
                        DateTimeFormatter().formatTimeFor12HourFormat(dateTime)
                    }
                } else if (DateTime.now().dayOfYear - it.dayOfYear <= 3) {
                    DateTimeFormatter().formatDateAsE(dateTime)
                } else {
                    DateTimeFormatter().formatDateAsMd(it)
                }
            } ?: let {
                ""
            }
        }
    }

}