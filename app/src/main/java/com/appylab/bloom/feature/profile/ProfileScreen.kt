package com.appylab.bloom.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.appylab.bloom.core.ui.BloomPeach
import com.appylab.bloom.core.ui.MistText
import com.appylab.bloom.core.ui.TextWhite
import com.appylab.bloom.core.ui.screenBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()
    var name by remember(profileState) { mutableStateOf(profileState.name) }
    var height by remember(profileState) { mutableStateOf(profileState.height) }
    var weight by remember(profileState) { mutableStateOf(profileState.weight) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBrush())
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Text("Profile", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextWhite)
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(BloomPeach),
                contentAlignment = Alignment.Center
            ) {
                Text("O", color = TextWhite, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(profileState.name.ifEmpty { "User" }, color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text("omkar@example.com", color = MistText, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (cm)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { viewModel.updateProfile(name, height, weight) }, modifier = Modifier.fillMaxWidth()) {
                Text("Save Changes")
            }
        }
    }
}
