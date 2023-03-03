package com.d83t.bpm.presentation.util

import android.content.res.Resources
import android.util.TypedValue
import org.joda.time.DateTime

val Int.dp: Int
    get() {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics)
            .toInt()
    }

fun DateTime.getKoreanHour(): String {
    val nowHour = this.hourOfDay
    return if (nowHour < 12) "오전 ${nowHour}시"
    else "오후 ${nowHour - 12}시"
}

fun DateTime.getUserScheduleDate(): String {
    return toString("yyyy.MM.dd") + " ${getKoreanHour()}"
}
