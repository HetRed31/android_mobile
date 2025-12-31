package com.example.art_space_5_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.art_space_5_4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                CenteredImageWithButtons()
            }
        }
    }
}

@Composable
fun CenteredImageWithButtons(modifier: Modifier = Modifier) {

    val resources = listOf(
        ImageAndText(image = R.drawable.kaktus1, text = R.string.title1),
        ImageAndText(image = R.drawable.kaktus2, text = R.string.title2),
        ImageAndText(image = R.drawable.kaktus3, text = R.string.title3),
        ImageAndText(image = R.drawable.kaktus4, text = R.string.title4),
        ImageAndText(image = R.drawable.kaktus5, text = R.string.title5),
    )

    var viewIndex by remember { mutableStateOf(0) } //текущ состояние

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resources[viewIndex].image),
            contentDescription = null,
            modifier = Modifier.size(400.dp)
        )
        Text(
            text = stringResource(resources[viewIndex].text),
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(2.dp)
                )
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = {
                if (viewIndex > 0) {  // Если не первая картинка
                    viewIndex--       // Просто уменьшаем на 1
                } else {              // Если первая (номер 0)
                    viewIndex = resources.size - 1  // Переходим к последней
                }
            }) { Text("Назад") }


            Button(onClick = {
                if (viewIndex < resources.size - 1) {  // Если не последняя картинка
                    viewIndex++       // Просто увеличиваем на 1
                } else {              // Если последняя
                    viewIndex = 0     // Возвращаемся к первой (номер 0)
                }
            }) { Text("Вперед") }
        }


    }
}

data class ImageAndText(val image: Int, val text: Int)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        CenteredImageWithButtons()
    }
}
