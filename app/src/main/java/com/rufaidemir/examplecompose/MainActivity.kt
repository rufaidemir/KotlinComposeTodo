package com.rufaidemir.examplecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rufaidemir.examplecompose.ui.theme.ExamplecomposeTheme
import androidx.compose.ui.draw.alpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rufaidemir.examplecompose.repository.PersonRepository
import com.rufaidemir.examplecompose.ui.components.AddTodoItemScreen
import com.rufaidemir.examplecompose.ui.components.TodoItem
import com.rufaidemir.examplecompose.ui.theme.shape
import com.rufaidemir.examplecompose.util.hexToComposeColor
import java.text.DateFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamplecomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}


//@Composable
//fun ColumnScope.CostumItem(weight: Float, color: Color = MaterialTheme.colorScheme.inversePrimary) {
//    Surface(
//        modifier = Modifier
//            .width(200.dp)
//            .height(50.dp)
//            .weight(weight),
//        color = color
//    ) {}
//}




val item = TodoItemData(
    isRepeat = true,
    isCancelled = false,
    startDate = System.currentTimeMillis(),
    todoTitle = "Yapilacak 1 Isim var",
    colorHex = "#2fc192",
    repeatInterval = 1256454L,
    repeatIntervalText = "Haftada Bir"
)
val item2 = TodoItemData(
    isRepeat = false,
    isCancelled = true,
    startDate = System.currentTimeMillis(),
    todoTitle = "Yapilacak 1 Isim var",
    colorHex = "#55c192",
    repeatIntervalText = "Haftada Bir"
)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamplecomposeTheme {
//        CustomText()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            ExpandableCard(title = "Kadere Bak", description = "Test strign ".repeat(25))
//            CustomTextField()
//            Spacer(modifier = Modifier.height(16.dp))
//            GoogleButton(
//                text = "Sign Up with Google",
//                loadingText = "Creating Account...",
//                onClicked = {})
//            Spacer(modifier = Modifier.height(16.dp))
//            CoilImage()
//            Spacer(modifier = Modifier.height(16.dp))
//            PasswordIndput()
//            FlatList()
            TodoItem(item)
            TodoItem(item2)
            Spacer(modifier = Modifier.height(10.dp))
            AddTodoItemScreen()

        }
    }
}

@Composable
fun GradientButton(text: String, textColor: Color, gradient: Brush, onClicked: () -> Unit) {
    Button(onClick = onClicked, colors = ButtonDefaults.buttonColors()) {

    }
}


@Composable
fun FlatList() {
    var personList = PersonRepository().getAllData()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = personList) {
            CustomItem(person = it)
        }

    }
}


@Composable
fun PasswordIndput() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val password = rememberSaveable { mutableStateOf("") }
        val showPassword = remember { mutableStateOf(false) }

        val iconVisibility = if (showPassword.value) {
            painterResource(R.drawable.baseline_visibility_24)
        } else {
            painterResource(R.drawable.baseline_visibility_off_24)
        }

        OutlinedTextField(
            value = password.value, onValueChange = { password.value = it },
            label = { Text("PassWord") },
            placeholder = { Text("Parola") },
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(painter = iconVisibility, contentDescription = null)
                }
            },
            visualTransformation = if (showPassword.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)


        )

    }

}

@Composable
fun CoilImage() { //NOT :BU componene uses permission internet izni olmadan hata verir
    Box(
        modifier = Modifier
            .height(150.dp)
            .height(150.dp), contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("http://placehold.it/120x120&text=image1").crossfade(true).build(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.test),
        )
        val painterState = painter.state
        Image(painter = painter, contentDescription = "Image Loho")
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }

    }
}


data class TodoItemData(
    val isRepeat: Boolean,
    val isCancelled: Boolean,
    val startDate: Long,
    val todoTitle: String,
    val colorHex:String,
    val repeatInterval: Long? =null,
    val repeatIntervalText: String?=null
)


@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(.8f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = "Sign Up with Google",
    loadingText: String = "Creating Account...",
    icon: Int = R.drawable.baseline_3mp_24,
    shape: Shape = shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.clickable { clicked = !clicked },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else text)
            if (clicked) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
                onClicked()
            }
        }
    }
}

@Composable
fun CustomTextField() {
    var text by remember { mutableStateOf("Type Here...") }

    OutlinedTextField(
        value = text, onValueChange = { newText -> text = newText },
        label = { Text(text = "Title") },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
            }
        },
        trailingIcon = {
            IconButton(onClick = { Log.d("Traiolinf Icon", "---------->Konsola yaz test") }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Check Mail")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Go
        )
    )

}

//@Composable
//fun CustomTextSelection(){
//    SelectionContainer() {
//        Column() {
//            Text(text = "Bu da yeni kadere bak ma fonk")
//            DisableSelection {
//                Text(
//                    text = "Disable text Bu da yeni kadere bak ma fonk",
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    modifier = Modifier.padding(10.dp)
//                )
//            }
//        }
//    }
//}

//@Composable
//fun CustomText() {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(
//            text = stringResource(id = R.string.app_name),
//            modifier = Modifier
//                .background(Color.Red)
//                .padding(16.dp)
//                .width(200.dp),
////            textAlign = TextAlign.End,
//        )
//        Text(
//            buildAnnotatedString {
//                withStyle(style = ParagraphStyle(textAlign = TextAlign.End)) {
//                    withStyle(
//                        style = SpanStyle(
//                            color = MaterialTheme.colorScheme.inversePrimary,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.ExtraBold
//                        )
//                    ) {
//                        append("A")
//                    }
//                    append("B")
//                    append("C")
//                    append("D")
//                }
//            },
//            modifier = Modifier
//                .background(Color.Blue)
//                .padding(16.dp)
//                .width(200.dp),
//        )
//        Text(text = "KadereBak".repeat(12), maxLines = 2, overflow = TextOverflow.Ellipsis)
//
//    }
//}

