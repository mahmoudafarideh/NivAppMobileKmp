package ir.niv.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.button.NivButtonSize
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.retry
import nivapp.composeapp.generated.resources.wifi_exclamation_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyListMessage(
    icon: Painter,
    message: String,
    buttonLabel: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier.padding(
            bottom = paddingValues.calculateBottomPadding(),
            top = paddingValues.calculateTopPadding()
        ).padding(24.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            tint = NivTheme.colorScheme.secondary,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = message,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
        NivButton(
            label = buttonLabel,
            onClick = onButtonClick,
            size = NivButtonSize.Small,
            modifier = Modifier.widthIn(max = 124.dp)
        )
    }
}

@Preview
@Composable
private fun EmptyListMessagePreview() {
    NivThemePreview {
        EmptyListMessage(
            icon = painterResource(Res.drawable.wifi_exclamation_24),
            message = "مشکلی پیش آمد! لطفا وضعیت اینترنت خود را بررسی کرده و مجدد تلاش کنید.",
            buttonLabel = stringResource(Res.string.retry),
            onButtonClick = {},
            modifier = Modifier
        )
    }
}


