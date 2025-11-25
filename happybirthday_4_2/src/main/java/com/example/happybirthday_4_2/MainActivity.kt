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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                .align(alignment = Alignment.CenterHorizontally) //выравнивание по центру
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