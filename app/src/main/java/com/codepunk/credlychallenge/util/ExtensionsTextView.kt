package com.codepunk.credlychallenge.util

import android.icu.text.DateTimePatternGenerator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.DateTimeException
import java.time.format.DateTimeFormatter

private const val DATE_SKELETON = "MMMM d, yyyy"

private val DATE_FORMATTER = DateTimeFormatter.ofPattern(
    DateTimePatternGenerator.getInstance()
        .getBestPattern(
            DATE_SKELETON,
            DateTimePatternGenerator.MATCH_ALL_FIELDS_LENGTH
        )
)

private const val GENRE_SEPARATOR = " | "

@BindingAdapter("bindLocalDate")
fun TextView.bindLocalDate(date: LocalDate?) {
    text = try {
        date?.toJavaLocalDate()?.format(DATE_FORMATTER) ?: ""
    } catch (e: DateTimeException) {
        "?"
    }
}

@BindingAdapter("bindGenres")
fun TextView.bindGenres(genres: List<String>) {
    text = genres.joinToString(GENRE_SEPARATOR)
}
