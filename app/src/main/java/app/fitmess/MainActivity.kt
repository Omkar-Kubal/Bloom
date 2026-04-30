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
import app.fitmess.ui.splash.BloomSplashScreen
import app.fitmess.ui.theme.FitMessTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitMessTheme {
                var showLogin by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(3_000)
                    showLogin = true
                }

                if (showLogin) {
                    BloomLoginScreen(modifier = Modifier.fillMaxSize())
                } else {
                    BloomSplashScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
