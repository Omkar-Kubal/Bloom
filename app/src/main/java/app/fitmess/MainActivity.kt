package app.fitmess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.fitmess.ui.dashboard.BloomDashboardScreen
import app.fitmess.ui.login.BloomLoginScreen
import app.fitmess.ui.signup.BloomSignupScreen
import app.fitmess.ui.splash.BloomSplashScreen
import app.fitmess.ui.theme.FitMessTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitMessTheme {
                var currentScreen by remember { mutableStateOf(BloomScreen.Splash) }
                var isGoogleContinuePending by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    delay(3_000)
                    currentScreen = BloomScreen.Login
                }

                when (currentScreen) {
                    BloomScreen.Splash -> {
                        BloomSplashScreen(modifier = Modifier.fillMaxSize())
                    }

                    BloomScreen.Login -> {
                        BloomLoginScreen(
                            modifier = Modifier.fillMaxSize(),
                            isGoogleContinuePending = isGoogleContinuePending,
                            onGoogleContinueClick = {
                                if (!isGoogleContinuePending) {
                                    isGoogleContinuePending = true
                                    coroutineScope.launch {
                                        delay(3_000)
                                        currentScreen = BloomScreen.Dashboard
                                        isGoogleContinuePending = false
                                    }
                                }
                            },
                            onCreateAccountClick = {
                                isGoogleContinuePending = false
                                currentScreen = BloomScreen.Signup
                            }
                        )
                    }

                    BloomScreen.Signup -> {
                        BloomSignupScreen(
                            modifier = Modifier.fillMaxSize(),
                            onBackClick = {
                                isGoogleContinuePending = false
                                currentScreen = BloomScreen.Login
                            },
                            onLoginClick = {
                                isGoogleContinuePending = false
                                currentScreen = BloomScreen.Login
                            }
                        )
                    }

                    BloomScreen.Dashboard -> {
                        BloomDashboardScreen(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

private enum class BloomScreen {
    Splash,
    Login,
    Signup,
    Dashboard
}
