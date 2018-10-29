/*
 * Copyright 2018 MadAppGang.
 *
 * Created by Andrii Fedorov afedorov@madappgang.com on 6/26/18.
 */

package chat.testapp.ui.formatter

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DateTimeFormatter {

    fun formatTimeFor24HourFormat(dateTime: DateTime?) =
        DateTimeFormat.forPattern("H:mm")?.print(dateTime) ?: ""

    fun formatTimeFor12HourFormat(dateTime: DateTime) =
        DateTimeFormat.forPattern("K:mm a")?.print(dateTime) ?: ""

    fun formatDateAsMd(dateTime: DateTime?) =
        DateTimeFormat.forPattern("MMM d")?.print(dateTime) ?: ""

    fun formatDateAsE(dateTime: DateTime) =
        DateTimeFormat.forPattern("E")?.print(dateTime) ?: ""
}