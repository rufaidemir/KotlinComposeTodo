package com.rufaidemir.examplecompose.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.MultipleColors
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import com.rufaidemir.examplecompose.R
import com.rufaidemir.examplecompose.model.TodoItem
import com.rufaidemir.examplecompose.util.hexToComposeColor
import java.text.DateFormat



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemView( item : TodoItem, modifyItem:(item:TodoItem)->Unit, onDeleteItem:(item:TodoItem)->Unit) {

    val (currentTag, setCurrentTag) = remember {mutableStateOf(item.tag)   }
    val primaryColor = MaterialTheme.colorScheme.primary
    var itemColor =if (item.hasColor && item.color!=null) Color(item.color!!) else primaryColor
    val (selectedColor, setSelectedColor) = remember { mutableStateOf(itemColor) }

    if(item.hasColor){
        item.color?.let{
            itemColor = Color(item.color!!)
        }
    }
    val rowHeight = 65.dp


    val taskTypeState = rememberUseCaseState()
    val options = listOf(
        Option(
            IconSource(R.drawable.baseline_done_24, tint = Color.Green),
            titleText = "Tamam",
//            selected = true
        ),
        Option(
            IconSource(R.drawable.baseline_access_time_24, tint = hexToComposeColor("#FFA500")),
            titleText = "Devam",
        ),
        Option(
            IconSource(R.drawable.baseline_cancel_24,tint = Color.Red),
            titleText = "Başarısız",
        ),
        Option(
            IconSource(R.drawable.baseline_error_outline_24, tint = MaterialTheme.colorScheme.tertiary),
            titleText = "İptal",
        ),
    )

    OptionDialog(
        state = taskTypeState,
        selection = OptionSelection.Single(options) { index, option ->
            item.tag = option.titleText
            item.hasTag = true
            setCurrentTag(option.titleText)
            modifyItem(item)
         },
        config = OptionConfig(mode = DisplayMode.GRID_VERTICAL),
    )

    val colorState = rememberUseCaseState()
    val templateColors = MultipleColors.ColorsInt(
        Color.Red.copy(alpha = 0.1f).toArgb(),
        Color.Red.copy(alpha = 0.3f).toArgb(),
        Color.Red.copy(alpha = 0.5f).toArgb(),
        Color.Red.toArgb(),
        Color.Green.copy(alpha = 0.1f).toArgb(),
        Color.Green.copy(alpha = 0.3f).toArgb(),
        Color.Green.copy(alpha = 0.5f).toArgb(),
        Color.Green.toArgb(),
        Color.Yellow.toArgb(),
        Color.Cyan.toArgb(),
        Color.LightGray.toArgb(),
        Color.Gray.toArgb(),
        Color.DarkGray.toArgb(),
        Color.Magenta.toArgb(),
    )


    ColorDialog(
        state = colorState, selection = ColorSelection(
            onSelectNone = {
                item.hasColor=false
                item.color=null
                setSelectedColor(primaryColor)
                modifyItem(item)
            },
            onSelectColor = {
                setSelectedColor(Color(it))
                item.hasColor=true
                item.color=it
                modifyItem(item)
            },
        ),
        config = ColorConfig(
            templateColors = templateColors
        )
    )

    val show = remember { mutableStateOf(true) }
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                show.value = false
                onDeleteItem(item)
                true
            } else false
        }, positionalThreshold = { 100.dp.toPx() }
    )


    AnimatedVisibility(
        show.value,exit = fadeOut(spring())
    ){

        SwipeToDismiss(state = dismissState, background = { DismissBackground(dismissState) }, dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300, easing = LinearOutSlowInEasing
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = selectedColor, shape = RoundedCornerShape(8.dp))
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
                            painterResource(id = getLeftIconId(currentTag.toString())),
                            tint = getLeftIconColor(currentTag.toString()),
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                taskTypeState.show()
                            }
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(selectedColor)
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
                            .background(selectedColor)
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
                                .background(selectedColor)
                                .clickable{
                                    colorState.show()
                                }
                        )
                    }
                }
            }
        } )
    }


}

fun getLeftIconId(currentTag: String): Int {
    var id=if (currentTag == "İptal") {
        (R.drawable.baseline_error_outline_24)
    } else if (currentTag == "Tamam") {
        (R.drawable.baseline_task_alt_24)
    } else if (currentTag== "Devam") {
        (R.drawable.baseline_access_time_24)
    } else if (currentTag == "Başarısız") {
        (R.drawable.baseline_cancel_24)
    } else {
        R.drawable.baseline_circle_24
    }
    return id

}
@Composable
fun getLeftIconColor(currentTag: String): Color {
    var color=if (currentTag == "İptal") {
        Color(33, 150, 243, 255)
    } else if (currentTag == "Tamam") {
        Color(7, 175, 69, 255)
    } else if (currentTag== "Devam") {
        Color(255, 152, 0, 255)
    } else if (currentTag == "Başarısız") {
        Color(244, 67, 54, 255)
    } else {
        MaterialTheme.colorScheme.background
    }
    return color

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color.Transparent
        DismissDirection.EndToStart -> Color.Transparent
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete",
            tint = Color.Red
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Delete,
            contentDescription = "delete",
            tint = Color.Red
        )
    }
}