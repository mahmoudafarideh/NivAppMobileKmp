package ir.niv.app.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.theme.shape.squircle.SquircleShape
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.fi_sr_marker
import nivapp.composeapp.generated.resources.fi_sr_person_simple
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SearchShimmer(
    items: Int = 5,
    modifier: Modifier = Modifier
) {
    val shape = remember { SquircleShape() }
    Column(modifier = modifier) {
        repeat(items) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 22.dp, horizontal = 24.dp)
                    .shimmer()
            ) {
                Spacer(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape)
                        .background(NivTheme.colorScheme.outlineVariant),
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column {
                    Spacer(
                        modifier = Modifier
                            .width(64.dp)
                            .height(12.dp)
                            .clip(NivTheme.shapes.large)
                            .background(NivTheme.colorScheme.outlineVariant),
                    )
                }
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
        SearchShimmer()
    }
}