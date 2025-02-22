package ir.niv.app.ui.login.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.niv.app.ui.core.FailedApi
import ir.niv.app.ui.login.components.OtpInput
import ir.niv.app.ui.login.components.PhoneInput
import ir.niv.app.ui.utils.LocalSnackBarHostState
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.button.NivButtonStyle
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.login_screen_title
import nivapp.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

const val PhoneNumberInputKey = "phone_number"

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val phoneError by viewModel.apiErrors(PhoneNumberInputKey).collectAsStateWithLifecycle()
    ErrorMessageObserve(state)
    Scaffold(
        modifier = modifier,
        content = {
            Box(modifier = Modifier.fillMaxSize().padding(it)) {
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

                    PhoneInput(
                        number = state.phoneNumber,
                        onNumberChange = {
                            viewModel.phoneNumberChanged(it)
                        },
                        error = phoneError,
                        readOnly = state.buttonUiModel != LoginUiModel.ButtonUiModel.RequestOtp,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        onEditClick = {
                            viewModel.editNumberClicked()
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    AnimatedVisibility(state.buttonUiModel != LoginUiModel.ButtonUiModel.RequestOtp) {
                        Column {
                            OtpInput(
                                otp = state.otp,
                                onOtpChange = {
                                    viewModel.otpChanged(it)
                                },
                                error = phoneError,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            )
                            Spacer(modifier = Modifier.height(64.dp))
                        }
                    }
                }
                NivButton(
                    label = when (state.buttonUiModel) {
                        LoginUiModel.ButtonUiModel.Login -> "ورود به حساب کاربری"
                        LoginUiModel.ButtonUiModel.Signup -> "ثبت نام"
                        LoginUiModel.ButtonUiModel.RequestOtp -> "قبول شرایط و ادامه"
                    },
                    onClick = {
                        viewModel.loginButtonClicked()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = NivButtonStyle.Primary,
                    state = state.buttonState
                )
            }
        },
    )
}

@Composable
private fun ErrorMessageObserve(state: LoginUiModel) {
    val localSnackBar = LocalSnackBarHostState.current
    LaunchedEffect(state.submitState) {
        (state.submitState as? FailedApi)?.error?.message?.let {
            localSnackBar.showSnackbar(
                message = it.name,
                withDismissAction = false,
                duration = SnackbarDuration.Short
            )
        }
    }
}