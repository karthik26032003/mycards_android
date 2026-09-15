package com.karthik.mycards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.karthik.mycards.security.RootChecker
import com.karthik.mycards.security.enableScreenSecurity
import com.karthik.mycards.ui.navigation.MyCardsNavHost
import com.karthik.mycards.ui.theme.MyCardsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single Activity that hosts all Compose screens.
 * @AndroidEntryPoint lets Hilt inject dependencies into screens below it.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableScreenSecurity()
        enableEdgeToEdge()
        setContent {
            // Start from the system setting, then let the user toggle it.
            val systemDark = isSystemInDarkTheme()
            var darkTheme by rememberSaveable { mutableStateOf(systemDark) }
            var showRootWarning by rememberSaveable {
                mutableStateOf(RootChecker.isDeviceRooted(applicationContext))
            }

            MyCardsTheme(darkTheme = darkTheme) {
                MyCardsNavHost(
                    isDarkTheme = darkTheme,
                    onToggleTheme = { darkTheme = !darkTheme }
                )

                if (showRootWarning) {
                    AlertDialog(
                        onDismissRequest = { showRootWarning = false },
                        title = { Text("Rooted device detected") },
                        text = {
                            Text(
                                "This device appears to be rooted. For your security, " +
                                    "some app protections may not be fully effective here."
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = { showRootWarning = false }) {
                                Text("I understand")
                            }
                        }
                    )
                }
            }
        }
    }
}
