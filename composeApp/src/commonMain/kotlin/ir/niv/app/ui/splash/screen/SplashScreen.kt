package ir.niv.app.ui.splash.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.niv.app.ui.core.isFailed
import ir.niv.app.ui.core.isLoading
import ir.niv.app.ui.core.onRetrieve
import ir.niv.app.ui.home.graph.HomeRoute
import ir.niv.app.ui.login.graph.LoginRoute
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.utils.LocalNavController
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.logo
import nivapp.composeapp.generated.resources.retry
import nivapp.composeapp.generated.resources.splash_app_info
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) {
        val viewModel: SplashViewModel = koinViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val navController = LocalNavController.current
        LaunchedEffect(state) {
            state.onRetrieve { isLoggedIn ->
                if (isLoggedIn) {
                    navController.popBackStack()
                    navController.navigate(HomeRoute)
                } else {
                    navController.popBackStack()
                    navController.navigate(LoginRoute)
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    tint = NivTheme.colorScheme.primary,
                    modifier = Modifier.size(76.dp)
                )
                Spacer(modifier = Modifier.size(48.dp))
                AnimatedVisibility(
                    visible = state.isLoading
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(36.dp),
                        strokeWidth = 4.dp,
                    )
                }

            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = state.isFailed
                ) {
                    Column {
                        NivButton(
                            label = stringResource(Res.string.retry),
                            onClick = {
                                viewModel.retryClicked()
                            },
                        )
                        Spacer(modifier = Modifier.size(24.dp))
                    }
                }
                Text(
                    modifier = Modifier.padding(bottom = 32.dp),
                    text = stringResource(Res.string.splash_app_info),
                    style = NivTheme.typography.bodyMedium
                )
            }
        }
    }
}