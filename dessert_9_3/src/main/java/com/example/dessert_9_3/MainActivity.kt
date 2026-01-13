package com.example.dessert_9_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dessert_9_3.ui.DessertReleaseApp
import com.example.dessert_9_3.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                DessertReleaseApp()
            }
        }
    }
}
