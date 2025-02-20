package ir.niv.app.ui.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.niv.app.ui.theme.theme.NivTheme

@Composable
fun PhoneInput(
    number: String,
    error: String?,
    readOnly: Boolean,
    onNumberChange: (String) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = number,
        onValueChange = onNumberChange,
        modifier = modifier,
        placeholder = {
            Text(text = "شماره موبایل")
        },
        trailingIcon = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = null,
                )
            }
        },
        leadingIcon = {
            AnimatedVisibility(readOnly) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onEditClick)
                )
            }
        },
        shape = NivTheme.shapes.extraLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone
        ),
        textStyle = LocalTextStyle.current.copy(
            textDirection = TextDirection.Ltr
        ),
        isError = error != null,
        supportingText = {
            AnimatedVisibility(
                error != null
            ) {
                Text(text = error.orEmpty())
            }
        },
        readOnly = readOnly

    )
}