package ir.niv.app.ui.previews

import androidx.compose.runtime.Composable
import ir.niv.app.ui.home.screen.HomeScreen
import ir.niv.app.ui.theme.theme.NivTheme

@NivPreview
@Composable
private fun HomeScreenPreview() {
    NivTheme {
        HomeScreen(
            avatar = null,
        )
    }
}