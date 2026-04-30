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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.fitmess.ui.login.BloomLoginScreen
import app.fitmess.ui.signup.BloomSignupScreen
import app.fitmess.ui.splash.BloomSplashScreen
import app.fitmess.ui.theme.FitMessTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitMessTheme {
                var currentScreen by remember { mutableStateOf(BloomScreen.Splash) }

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
                            onCreateAccountClick = {
                                currentScreen = BloomScreen.Signup
                            }
                        )
                    }

                    BloomScreen.Signup -> {
                        BloomSignupScreen(
                            modifier = Modifier.fillMaxSize(),
                            onBackClick = {
                                currentScreen = BloomScreen.Login
                            },
                            onLoginClick = {
                                currentScreen = BloomScreen.Login
                            }
                        )
                    }
                }
            }
        }
    }
}

private enum class BloomScreen {
    Splash,
    Login,
    Signup
}
