package com.example.bus_schedule_9_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bus_schedule_9_2.ui.BusScheduleApp
import com.example.bus_schedule_9_2.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                BusScheduleApp()
            }
        }
    }
}
