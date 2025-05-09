package ir.niv.app.ui.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import nivapp.composeapp.generated.resources.cross_small_24
import nivapp.composeapp.generated.resources.fi_br_bars_filter
import org.jetbrains.compose.resources.painterResource


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = NivTheme.typography.bodyLarge,
                decorationBox = {
                    it()
                    if (query.isBlank()) {
                        Text(
                            text = "جستجوی باشگاه...",
                            style = NivTheme.typography.bodyLarge,
                            color = NivTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(
                painter = painterResource(Res.drawable.arrow_small_right_24),
                onClick = onBackButtonClick
            )
        },
        actions = {
            //TODO: Add the filter here to make the search result filterable
//            IconButton(
//                painter = painterResource(Res.drawable.fi_br_bars_filter),
//                onClick = {
//                    onQueryChange("")
//                }
//            )
            AnimatedVisibility(
                visible = query.isNotBlank(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                IconButton(
                    painter = painterResource(Res.drawable.cross_small_24),
                    onClick = {
                        onQueryChange("")
                    }
                )
            }
        }
    )
}