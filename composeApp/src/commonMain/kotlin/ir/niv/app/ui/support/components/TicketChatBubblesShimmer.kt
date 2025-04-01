package ir.niv.app.ui.support.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ir.niv.app.ui.theme.theme.NivTheme

@Composable
fun TicketChatBubblesShimmer(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier.fillMaxWidth().padding(
                end = 16.dp,
                bottom = 16.dp,
                start = 32.dp
            ).shimmer(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                12.dp,
                                12.dp,
                                2.dp,
                                12.dp
                            )
                        )
                        .background(NivTheme.colorScheme.tertiaryContainer)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(NivTheme.shapes.large)
                        .background(NivTheme.colorScheme.outlineVariant)
                        .padding(8.dp)
                        .width(32.dp)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Spacer(
                Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(NivTheme.colorScheme.outlineVariant)
            )
        }
        Column(
            modifier = modifier.fillMaxWidth().padding(
                start = 16.dp,
                bottom = 16.dp,
                end = 32.dp
            ).shimmer(),
        ) {
            Spacer(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp,
                            2.dp,
                            12.dp,
                            12.dp
                        )
                    )
                    .background(NivTheme.colorScheme.secondaryContainer)
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clip(NivTheme.shapes.large)
                    .background(NivTheme.colorScheme.outlineVariant)
                    .padding(8.dp)
                    .width(32.dp)
            )
        }
    }
}