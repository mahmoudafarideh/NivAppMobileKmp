package niv.design.designsystem.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import niv.design.designsystem.theme.NivTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NivButton(
    label: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: NivButtonState = NivButtonState.Enable,
    style: NivButtonStyle = NivButtonStyle.Primary,
    size: NivButtonSize = NivButtonSize.Large,
    icon: DrawableResource? = null,
) {
    val buttonColors = ButtonDefaults.buttonColors().copy(
        contentColor = style.getContentColor(state),
        containerColor = style.getBackgroundColor(state)
    )
    Button(
        onClick = onClick,
        modifier = modifier.height(size.height),
        colors = buttonColors,
        elevation = buttonElevation(style),
        enabled = state == NivButtonState.Enable
    ) {
        AnimatedContent(
            state == NivButtonState.Loading
        ) {
            if (it) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(size.height)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp).align(Alignment.Center)
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(size.height)
                ) {
                    label?.let {
                        Text(
                            text = it,
                            style = size.textStyle(),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    icon?.let {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(size.iconSize)
                                .padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun buttonElevation(style: NivButtonStyle) =
    if (style == NivButtonStyle.Neutral) {
        ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 4.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 2.dp,
            disabledElevation = 2.dp
        )
    } else {
        null
    }

@Preview
@Composable
private fun NivButtonPreview() {
    NivTheme {
        NivButton(
            label = "سلام",
            onClick = {}
        )
    }
}


