package com.rufaidemir.examplecompose.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.rufaidemir.examplecompose.R
import com.rufaidemir.examplecompose.TodoItemData
import com.rufaidemir.examplecompose.ui.theme.ExamplecomposeTheme
import com.rufaidemir.examplecompose.util.colorToHex
import com.rufaidemir.examplecompose.util.hexToComposeColor
import com.rufaidemir.examplecompose.util.stringToLocalDateTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset


@Preview
@Composable
fun addTodoPreview(){
    ExamplecomposeTheme {
        AddTodoItemScreen()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoItemScreen() {
    // Ekleme için giriş bilgilerini tutacak mutable state'leri oluşturuyoruz
    val (isRepeat, setIsRepeat) = remember { mutableStateOf(false) }
    val (isCancelled, setIsCancelled) = remember { mutableStateOf(false) }
    val (startDate, setStartDate) = remember { mutableStateOf(System.currentTimeMillis()) }
    val (todoTitle, setTodoTitle) = remember { mutableStateOf("") }
    var ic = MaterialTheme.colorScheme.primary
    val (colorHex, setColorHex) = remember { mutableStateOf(colorToHex(ic)) }
    val (repeatInterval, setRepeatInterval) = remember { mutableStateOf<Long?>(null) }
    val (repeatIntervalText, setRepeatIntervalText) = remember { mutableStateOf("") }

    val (hasColor, sethasColor) = remember { mutableStateOf(false) }
    val (showColorPicker, setShowColorPicker) = remember { mutableStateOf(false) }


    val (hasTime, setHasTime) = remember { mutableStateOf(false) }
    val calendarState = rememberUseCaseState()
    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }

    val clockState = rememberUseCaseState()
    val (selectedClockText, setSelectedClockText) = remember { mutableStateOf("00:00")    }


    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate.value,
            onNegativeClick = { calendarState.hide() }
        ) { newDate ->
            selectedDate.value = newDate
            setStartDate(stringToLocalDateTime("${selectedDate.value.toString()} $selectedClockText").toEpochSecond(
                ZoneOffset.UTC))
        },
    )

    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes{hours, minutes ->
            val formattedHour = String.format("%02d", hours)
            val formattedMinute = String.format("%02d", minutes)
            println("Selected Hour : $formattedHour : $formattedMinute")
            setSelectedClockText( "$formattedHour:$formattedMinute")
            setStartDate(stringToLocalDateTime("${selectedDate.value.toString()} $selectedClockText").toEpochSecond(
                ZoneOffset.UTC))
        },
        config = ClockConfig(is24HourFormat = true,)
    )

    // Ekran tasarımını oluşturuyoruz
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = todoTitle,
            onValueChange = { setTodoTitle(it) },
            label = { Text("Todo Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

//        --------------------------------ADD REOMVE ICONS----------------------------------------------
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            if(!hasColor){
                Icon(painter = painterResource(id = R.drawable.color),
                    contentDescription = "AddColor",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            sethasColor(true)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if(!hasTime){
                Icon(painter = painterResource(id = R.drawable.baseline_notification_add_24),
                    contentDescription = "Add Time",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            setHasTime(true)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if(!isRepeat){
                Icon(painter = painterResource(id = R.drawable.baseline_repeat_24),
                    contentDescription = "Add Repeat",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            setIsRepeat(true)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
        if(hasColor){
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .border(
                        0.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = Color.White
                    )
                    .clip(shape = MaterialTheme.shapes.medium)
                    .clickable {
                        setShowColorPicker(!showColorPicker)
                    }
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.round_square_24),
                    contentDescription = "Color",
                    modifier = Modifier.size(24.dp),
                    tint = hexToComposeColor(colorHex)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = colorHex, color = hexToComposeColor(colorHex),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .weight(1f),
                )

                Icon(painter = painterResource(id = R.drawable.baseline_cancel_24),
                    contentDescription = "Cancel",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            sethasColor(false)
                        },
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))


                if (showColorPicker) {
                    customDialog(
                        onConfirm = {
                                selectColor->setColorHex(selectColor)
                        },
                        onDismiss = { setShowColorPicker(false) },
                        initialColor = hexToComposeColor(colorHex)
                    )
                }
            }
        }

        // Tarih Seçiciyi ekliyoruz
        if(hasTime){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        0.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = Color.White
                    )
                    .padding(start = 16.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier
                    .weight(1f)
                    .clickable { calendarState.show() }
                    .clip(shape = MaterialTheme.shapes.medium),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_date_range_24),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedDate.value.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
                //            ---------------------------------ADD CLOCK ORNONE--------------
                Row(modifier = Modifier
                    .weight(1f)
                    .clickable { clockState.show() }
                    .clip(shape = MaterialTheme.shapes.medium),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedClockText,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
                Icon(painter = painterResource(id = R.drawable.baseline_cancel_24),
                    contentDescription = "Cancel",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            setHasTime(false)
                        },
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))

            }
        }

        if(isRepeat){
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        0.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = Color.White
                    )
                    .padding(start = 16.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
            ) {
                Row(modifier = Modifier
                    .weight(1f)
                    .clickable { }
                    .clip(shape = MaterialTheme.shapes.medium),
                    verticalAlignment = Alignment.CenterVertically){

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_repeat_24),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
//                Checkbox(
//                    checked = isRepeat,
//                    onCheckedChange = { setIsRepeat(it) }
//                )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Tekrar")
                }
                Row(modifier = Modifier
                    .weight(3f)
                    .clickable { calendarState.show() }
                    .clip(shape = MaterialTheme.shapes.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){
                    val (selectedItemInterval, setSelectedItemInterval) = remember {mutableStateOf(1)}

                    TextField(
                        value = selectedItemInterval.toString(),
                        onValueChange = {
                            val interval = it.toIntOrNull()
                            if(interval!=null){
                                setSelectedItemInterval(interval)
                            }else{
                                setSelectedItemInterval(1)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent),
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp)
                    )

                    val (isExpanded, setIsExpanded) = remember {
                        mutableStateOf(false)
                    }
                    val dropdownItems = listOf<String>("Dakika" , "Saat", "Gun", "Hafta","Ay", "Yil")
                    val (selectedItemText, setSelectedItemText) = remember {mutableStateOf("Gun")}


                    Box(modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.TopStart)) {
                        Text(selectedItemText, textAlign = TextAlign.Center,modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { setIsExpanded(true) })
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                            fontSize = 16.sp)
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { setIsExpanded(false) },
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            dropdownItems.forEachIndexed{ index, s ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = s, fontSize = 16.sp)
                                    },
                                    onClick = {
                                        setSelectedItemText(s)
                                        setIsExpanded(false)
                                    })
                            }
                        }
                    }
                    Icon(painter = painterResource(id = R.drawable.baseline_cancel_24),
                        contentDescription = "Cancel",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                setIsRepeat(false)
                            },
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

            }
        }


        Button(
            onClick = {
                // Verileri TodoItemData sınıfından oluşturup, onAddItem callback'ini çağırıyoruz
                val todoItem = TodoItemData(
                    isRepeat = isRepeat,
                    isCancelled = isCancelled,
                    startDate = startDate,
                    todoTitle = todoTitle,
                    colorHex = colorHex,
                    repeatInterval = repeatInterval,
                    repeatIntervalText = repeatIntervalText
                )
//                onAddItem(todoItem)
            },
            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(Color.Green)
        ) {
            Text(text = "Add Todo")
        }
    }
}