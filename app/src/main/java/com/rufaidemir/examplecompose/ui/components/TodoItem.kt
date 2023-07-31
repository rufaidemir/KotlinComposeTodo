package com.rufaidemir.examplecompose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rufaidemir.examplecompose.R
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.ui.theme.ExamplecomposeTheme
import java.text.DateFormat


@Preview(showBackground = true)
@Composable
fun TodoItemPreview(){
    ExamplecomposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                val item1 = TodoItem(title = "Kadere Bak", hasColor = false, hasTime = false, time = System.currentTimeMillis(),
                    hasInterval = true,
                    interval = 15454545,
                    intervalText = "1 Ay",
                    hasTag = false)
                val item2 = TodoItem(title = "Kadere Bakmadan olmaz bu isler", hasColor = false,time = System.currentTimeMillis(),
                    color = Color.Red.toArgb(), hasTime = false, hasInterval = false,
                    hasTag = true,
                    tag = "Devam"
                )

                TodoItemView(item = item1)
                Spacer(modifier = Modifier.height(8.dp))
                TodoItemView(item = item2)
            }

        }
    }
}

@Composable
fun TodoItemView( item : TodoItem) {

    var itemColor =  MaterialTheme.colorScheme.primary
    if(item.hasColor){
        item.color?.let{
            itemColor = Color(item.color)
        }
    }
    val rowHeight = 65.dp
    val leftIcon = if (item.hasInterval) {
        painterResource(id = R.drawable.baseline_repeat_24)
    } else if (item.hasTag && item.tag=="İptal") {
        painterResource(id = R.drawable.baseline_error_outline_24)
    } else if (item.hasTag && item.tag=="Tamam") {
        painterResource(id = R.drawable.baseline_task_alt_24)
    } else if (item.hasTag && item.tag=="Devam") {
        painterResource(id = R.drawable.baseline_access_time_24)
    }else if (item.hasTag && item.tag=="Başarısız") {
        painterResource(id = R.drawable.baseline_cancel_24)
    } else {
        painterResource(id = R.drawable.baseline_circle_24)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp,color=itemColor, shape = RoundedCornerShape(8.dp))
                .height(rowHeight), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
                    .height(rowHeight),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    leftIcon,
                    contentDescription = "",
                    tint = if (item.hasTag && item.tag == "İptal") {
                        Color.Red
                    } else {
                        itemColor
                    }
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(itemColor)
            )
            Column(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = DateFormat.getInstance().format(item.time),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                    if (item.hasInterval && item.interval!=null){
                        Text(
                            text ="${item.intervalText}",
                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_repeat_24),
                            modifier = Modifier.size(12.dp),
                            contentDescription ="",
                            tint = itemColor
                        )
                    }
                }
                Text(
                    text = item.title,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = TextStyle(textDecoration = if (item.hasTag && item.tag=="Iptal") TextDecoration.LineThrough else TextDecoration.None)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(itemColor)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(10.dp)
                        .width(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(itemColor)

                )
            }
        }

    }
}