package com.dovohmichael.fixercurrencyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dovohmichael.fixercurrencyapp.currency.presentation.CurrencyViewState
import com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter.CurrencyBodyContent
import com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter.HomeScreen
import com.dovohmichael.fixercurrencyapp.currency.presentation.viewmodel.CurrencyViewModel
import com.dovohmichael.fixercurrencyapp.ui.theme.FixerCurrencyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FixerCurrencyAppTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen(context = this)
            }
        }
    }
}


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HistoryBodyContent(
    currencyViewModel: CurrencyViewModel= hiltViewModel()
) {

    val viewState by currencyViewModel.viewState.collectAsStateWithLifecycle()
    //val viewState by remember{ mutableStateOf(currencyViewModel.viewState) }

    Column(modifier = Modifier.padding(24.dp, 0.dp)) {

//        if(viewState is CurrencyViewState.Loading){
//            LoadingAnimation()
//        }
//        else
//        Greeting("MICHAEL $viewState")


    }

}

@Composable
fun LoadingAnimation(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1500
) {

    // 3 circles
    val circles = listOf(
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            // Use coroutine delay to sync animations
            // divide the animation delay by number of circles
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    // outer circle
    Box(
        modifier = Modifier
            .size(size = 200.dp)
            .background(color = Color.Transparent)
    ) {
        // animating circles
        circles.forEachIndexed { index, animatable ->
            Box(
                modifier = Modifier
                    .scale(scale = animatable.value)
                    .size(size = 200.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = (1 - animatable.value))
                    )
            ) {
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FixerCurrencyAppTheme {
        Greeting("Android")
    }
}