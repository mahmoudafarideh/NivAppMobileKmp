package ir.niv.app.ui.previews

import androidx.compose.runtime.Composable
import ir.niv.app.ui.home.models.HomeGridItemUiModel
import ir.niv.app.ui.home.models.UserUiModel
import ir.niv.app.ui.home.screen.HomeScreen
import ir.niv.app.ui.theme.theme.NivThemePreview

@NivPreview
@Composable
private fun HomeScreenPreview() {
    NivThemePreview {
        HomeScreen(
            user = UserUiModel(
                avatar = "",
                name = "محمود"
            ),
            grid = HomeGridItemUiModel.getItems()
        )
    }
}