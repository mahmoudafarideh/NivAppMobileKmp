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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.niv.app.NivTheme
import ir.niv.app.domain.splash.LoggedInUserRepository
import ir.niv.app.ui.core.isLoading
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) {
        val viewModel = remember {
            SplashViewModel(LoggedInUserRepository())
        }
        val state = viewModel.state.collectAsStateWithLifecycle().value
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

            Text(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
                text = "نیواپ | v1.0.0",
                style = NivTheme.typography.bodyMedium
            )
        }
    }
}