package ir.niv.app.ui.support.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TicketsShimmer(
    items: Int = 5,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        repeat(items) {
            Row(modifier = Modifier.padding(vertical = 22.dp, horizontal = 24.dp)) {
                Spacer(
                    modifier = Modifier
                        .shimmer()
                        .height(12.dp)
                        .width(76.dp)
                        .clip(NivTheme.shapes.medium)
                        .background(NivTheme.colorScheme.outlineVariant)
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(start = 24.dp),
                color = NivTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Preview
@Composable
private fun TicketsShimmerPreview() {
    NivThemePreview {
        TicketsShimmer()
    }
}