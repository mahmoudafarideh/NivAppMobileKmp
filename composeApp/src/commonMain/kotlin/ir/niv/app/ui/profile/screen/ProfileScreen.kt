package ir.niv.app.ui.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.profile.components.ProfileActionRow
import ir.niv.app.ui.profile.components.UserAvatar
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import m.a.compilot.navigation.controller.LocalNavController
import m.a.compilot.navigation.controller.comPilotNavController
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import nivapp.composeapp.generated.resources.fi_sr_arrow_small_left
import nivapp.composeapp.generated.resources.fi_sr_user_lock
import nivapp.composeapp.generated.resources.fi_sr_user_pen
import nivapp.composeapp.generated.resources.fi_sr_user_trust
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    avatar: String,
    userName: String,
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavController.comPilotNavController
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        painter = painterResource(Res.drawable.arrow_small_right_24),
                        onClick = {
                            navigation.safePopBackStack()
                        }
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            Spacer(modifier = Modifier.size(12.dp))
            UserAvatar(
                avatar = avatar,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = userName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = NivTheme.colorScheme.onBackground,
                style = NivTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(16.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = NivTheme.colorScheme.outlineVariant
            )
            ProfileActionRow(
                resource = Res.drawable.fi_sr_user_pen,
                label = "ویرایش پروفایل",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            ProfileActionRow(
                resource = Res.drawable.fi_sr_user_trust,
                label = "تکمیل اطلاعات کاربری",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            ProfileActionRow(
                resource = Res.drawable.fi_sr_user_lock,
                label = "تغییر کد ورود",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun SearchMapScreenPreview() {
    NivThemePreview {
        ProfileScreen(
            avatar = "",
            userName = "محمود آفریده"
        )
    }
}