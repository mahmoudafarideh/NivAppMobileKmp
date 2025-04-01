package ir.niv.app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.retry
import nivapp.composeapp.generated.resources.wifi_exclamation_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConnectLost(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    EmptyListMessage(
        icon = painterResource(Res.drawable.wifi_exclamation_24),
        message = "مشکلی پیش آمد! لطفا وضعیت اینترنت خود را بررسی کرده و مجدد تلاش کنید.",
        buttonLabel = stringResource(Res.string.retry),
        onButtonClick = onRetryClick,
        modifier = modifier,
        paddingValues = paddingValues
    )
}