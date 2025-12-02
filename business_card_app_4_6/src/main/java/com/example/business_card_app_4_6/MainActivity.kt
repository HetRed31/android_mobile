package com.example.business_card_app_4_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.business_card_app_4_6.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BusinessCardapp()
                }
            }
        }
    }
}

@Composable
fun BusinessCardapp() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        Column(
            modifier = Modifier
                .weight(1f) // Занимает всё доступное пространство
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center, // Центрируем по вертикали
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Box{
            val image = painterResource(R.drawable.icons_card)
            Image(
                painter = image,
                contentDescription = "Profile photo",
                modifier = Modifier.size(100.dp)


            )
            }
            Text(
                fontSize = 30.sp,
                text = "Name",
                modifier = Modifier.padding(top = 10.dp, bottom = 1.dp),
            )
            Text(
                text = "Title",
                fontSize = 12.sp
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp), // Отступ снизу
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_phone_black),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(
                    text = "+00 (00) 000 000",
                    fontSize = 12.sp
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_share_black),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(
                    text = "@Alex.com",
                    fontSize = 12.sp
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_email_black),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(
                    text = "Hetred@mail.com",
                    fontSize = 12.sp
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        BusinessCardapp()
    }
}
