package com.rufaidemir.examplecompose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rufaidemir.examplecompose.R
import com.rufaidemir.examplecompose.model.TodoItem
import java.text.DateFormat





@Composable
fun TodoItem( item : TodoItem) {

    val itemColor = if(item.hasColor) Color(item.color!!) else MaterialTheme.colorScheme.background
    val rowHeight = 60.dp
    val leftIcon = if (item.hasInterval) {
        painterResource(id = R.drawable.baseline_repeat_24)
    } else if (item.hasTag && item.tag=="Iptal") {
        painterResource(id = R.drawable.baseline_cancel_24)
    } else {
        painterResource(id = R.drawable.baseline_fireplace_24)
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
                .height(rowHeight), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
//                    .background(MaterialTheme.colorScheme.secondary)
                    .height(rowHeight),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    leftIcon,
                    contentDescription = "",
                    tint = if (item.hasTag && item.tag == "Iptal") {
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
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
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
                    .fillMaxHeight(),
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