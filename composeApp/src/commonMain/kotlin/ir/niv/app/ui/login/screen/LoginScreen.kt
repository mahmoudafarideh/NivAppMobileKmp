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
import ir.niv.app.ui.home.graph.HomeRoute
import ir.niv.app.ui.login.components.OtpInput
import ir.niv.app.ui.login.components.PhoneInput
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.button.NivButtonStyle
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.utils.LocalNavController
import ir.niv.app.ui.utils.LocalSnackBarHostState
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.login_button_enter
import nivapp.composeapp.generated.resources.login_button_register
import nivapp.composeapp.generated.resources.login_button_signup
import nivapp.composeapp.generated.resources.login_screen_title
import nivapp.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

const val PhoneNumberInputKey = "phone_number"
const val OtpInputKey = "otp"

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val phoneError by viewModel.apiErrors(PhoneNumberInputKey).collectAsStateWithLifecycle()
    val otpError by viewModel.apiErrors(OtpInputKey).collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    ErrorMessageObserve(state)
    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collect {
            navController.navigate(HomeRoute)
        }
    }
    Scaffold(
        modifier = modifier,
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
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
                                error = otpError,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            )
                            Spacer(modifier = Modifier.height(64.dp))
                        }
                    }
                }
                NivButton(
                    label = stringResource(
                        when (state.buttonUiModel) {
                            LoginUiModel.ButtonUiModel.Login -> Res.string.login_button_enter
                            LoginUiModel.ButtonUiModel.Signup -> Res.string.login_button_signup
                            LoginUiModel.ButtonUiModel.RequestOtp -> Res.string.login_button_register
                        }
                    ),
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