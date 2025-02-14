package ir.niv.app.ui.login.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import niv.design.designsystem.button.NivButton
import niv.design.designsystem.button.NivButtonStyle
import niv.design.designsystem.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.login_screen_title
import nivapp.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.logo),
                        tint = NivTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = stringResource(Res.string.login_screen_title),
                        style = NivTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    val number = remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = number.value,
                        onValueChange = {
                            number.value = it
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        placeholder = {
                            Text(
                                text = "شماره تماس"
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Phone,
                                contentDescription = null,
                            )
                        },
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    NivButton(
                        label = "قبول شرایط و ادامه",
                        onClick = {

                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        style = NivButtonStyle.Primary,
                    )
                }
            }
        },
    )
}