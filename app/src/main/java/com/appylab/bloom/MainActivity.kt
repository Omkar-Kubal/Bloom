package com.appylab.bloom

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import com.appylab.bloom.feature.run.StepCounterService
import com.appylab.bloom.navigation.BloomApp
import com.appylab.bloom.ui.theme.BloomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        maybeStartStepService()

        enableEdgeToEdge()
        setContent {
            BloomTheme(dynamicColor = false, darkTheme = true) {
                Surface {
                    BloomApp()
                }
            }
        }
    }

    fun maybeStartStepService() {
        val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Permission not required below API 29
        }
        if (permissionGranted) {
            ContextCompat.startForegroundService(this, Intent(this, StepCounterService::class.java))
        }
    }
}
