package com.example.happybirthday_4_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday_4_2.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    GreetingText(
                        message = "Happy Birthday Sam!",
                        from = "From Emma",
                        modifier = Modifier.padding(8.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier



//        modifier = modifier.padding(8.dp), // отступы вокруг столбца
//           verticalArrangement = Arrangement.Center //выровнять приветствие по центру экрана
        )
    {

        Text(
            text = message,
            fontSize = 100.sp, //размер шрифта
            lineHeight = 116.sp, //высота строки
            textAlign = TextAlign.Center //центр текста
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp) //Отступы
                .align(alignment = Alignment.End) //выравнивание по правому краю
        )
    }

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        GreetingText(message = "Happy Birthday Sam!", from = "From Alex"
        )
    }
}