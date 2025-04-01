package ir.niv.app.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

//private val LatinNumbers = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
private val PersianNumbers = listOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

@Composable
@ReadOnlyComposable
fun String.localized(): String {
    return buildString {
        this@localized.forEach {
            if(it.isDigit()) {
                append(PersianNumbers["$it".toInt()])
            } else {
                append(it)
            }
        }
    }
}