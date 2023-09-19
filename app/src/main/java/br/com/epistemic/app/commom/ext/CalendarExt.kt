package br.com.epistemic.app.commom.ext

import android.content.Context
import android.provider.Settings
import br.com.epistemic.app.R
import br.com.epistemic.app.domain.repository.LANGUAGES
import java.util.Calendar

fun getWelcomeMessage(c: Context, languageTag: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val year = calendar.get(Calendar.YEAR)

    val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> c.getString(R.string.sunday)
        Calendar.MONDAY -> c.getString(R.string.monday)
        Calendar.TUESDAY -> c.getString(R.string.tuesday)
        Calendar.WEDNESDAY -> c.getString(R.string.wednesday)
        Calendar.THURSDAY -> c.getString(R.string.thursday)
        Calendar.FRIDAY -> c.getString(R.string.friday)
        Calendar.SATURDAY -> c.getString(R.string.satuday)
        else -> ""
    }

    val month = when(calendar.get(Calendar.MONTH)) {
        Calendar.JANUARY -> c.getString(R.string.january)
        Calendar.FEBRUARY -> c.getString(R.string.february)
        Calendar.MARCH -> c.getString(R.string.march)
        Calendar.APRIL -> c.getString(R.string.april)
        Calendar.MAY -> c.getString(R.string.may)
        Calendar.JUNE -> c.getString(R.string.june)
        Calendar.JULY -> c.getString(R.string.july)
        Calendar.AUGUST -> c.getString(R.string.august)
        Calendar.SEPTEMBER -> c.getString(R.string.september)
        Calendar.OCTOBER -> c.getString(R.string.october)
        Calendar.NOVEMBER -> c.getString(R.string.november)
        Calendar.DECEMBER -> c.getString(R.string.december)
        else -> ""
    }


    val finalStr = when(languageTag) {
        LANGUAGES.PT_BR.tag -> buildString {
            append(dayOfWeek)
            append(", ")
            append(dayOfMonth)
            append(" ", "de", " ")
            append(month)
            append(" ", "de", " ")
            append(year)
        }
        LANGUAGES.EN_US.tag -> buildString {
            append(dayOfWeek)
            append(", ")
            append(month)
            append(" ")
            append(dayOfMonth)
            append(", ")
            append(year)
        }
        else -> buildString {
            append(dayOfWeek)
            append(", ")
            append(dayOfMonth)
            append(" ", "de", " ")
            append(month)
            append(" ", "de", " ")
            append(year)
        }
    }

    return finalStr
}