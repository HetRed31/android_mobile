package com.example.happybirthday_4_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
                    GreetingImage(
                        message = "Happy Birthday Sam!",
                        from = "From Emma"
                    )

                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        Text(
            text = message,                      // Текст для отображения
            fontSize = 70.sp,                    // Размер шрифта: 70 масштабируемых пикселей
            lineHeight = 80.sp,                  // Высота строки для многострочного текста
            textAlign = TextAlign.Center,        // Горизонтальное выравнивание по центру
            color = Color.Blue,                  // Цвет текста: синий
            fontWeight = FontWeight.Bold,        // Жирное начертание шрифта
            modifier = Modifier                  // Модификатор для настройки элемента
                .padding(top = 100.dp)           // Отступ сверху: 100 плотностно-независимых пикселей
        )
        Text(
            text = from,
            fontSize = 28.sp,
            color = Color.Red,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.androidparty)
    Box( modifier) { //наложения друг на друга
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop, //равномерное растягивание изображения
            alpha = 0.5F                     //прозрачность
        )
        GreetingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        GreetingImage(message = stringResource(R.string.happy_birthday_text), from = stringResource(
            R.string.signature_text
        )
                                                //Закодированое сообще в strings.xml
        )
    }
}