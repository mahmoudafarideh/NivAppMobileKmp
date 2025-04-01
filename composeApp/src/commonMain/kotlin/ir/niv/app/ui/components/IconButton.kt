package ir.niv.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.theme.NivTheme

@Composable
fun IconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = NivTheme.colorScheme.primary
) {
    Icon(
        painter = painter,
        tint = contentColor,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onClick()
            }
            .padding(12.dp)
            .size(24.dp)
    )
}

@Composable
fun IconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = imageVector,
        tint = NivTheme.colorScheme.primary,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .padding(12.dp)
            .size(24.dp)
    )
}