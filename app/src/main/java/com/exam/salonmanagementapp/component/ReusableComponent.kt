package com.exam.salonmanagementapp.component

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.Screen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = ""
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIconImageVector,
                    contentDescription = leadingIconDescription,
                    tint = if(showError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black
            ),
            isError = showError,
            trailingIcon = {
                if (showError && !isPasswordField) Icon(imageVector = Icons.Filled.Error, contentDescription = "Show error Icon")
                if (isPasswordField) {
                    IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                        Icon(
                            imageVector = if(isPasswordField) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            },
            visualTransformation = when {
                isPasswordField && isPasswordVisible -> VisualTransformation.None
                isPasswordField -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true
        )
        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = (-8).dp)
                    .fillMaxWidth(fraction = 0.9f)
            )
        }
    }
}


@Composable
fun TitleText(
    text: String
) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun CustomerBackground(
    navController: NavController,
    content: @Composable ()-> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
    val customerId = sharedPreference.getString("customerId", null)
    if (customerId == null) {
        navController.navigate(route = Screen.CustomerLanding.route)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .paint(
                painter = painterResource(R.drawable.salon_bg2),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                    .background(
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText(text = "Hello")
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(35.dp)
                        .clip(shape = RoundedCornerShape(20.dp)),
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    .fillMaxWidth(0.9f)
                    .padding(20.dp),
            ) {
                content()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.8f)
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        navController.navigate(route = Screen.CustomerAppointment.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.EditCalendar,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CustomDropDownMenu(
    label : String = "Please select",
    services : List<String>,
    isExpanded: Boolean = false,
    onExpanded: (Boolean) -> Unit = {},
    selectedItem: String,
    onValueChange: (String) -> Unit,
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    showError: Boolean = false,
    errorMessage: String = "",
){
    var textSize by remember { mutableStateOf(Size.Zero) }
    val icon = if(isExpanded){
        Icons.Filled.ArrowDropUp
    }else{
        Icons.Filled.ArrowDropDown
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedItem,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .onGloballyPositioned { coordinates ->
                    textSize = coordinates.size.toSize()
                },
            label = {Text(text = label)},
            leadingIcon = {
                Icon(
                    imageVector = leadingIconImageVector,
                    contentDescription = leadingIconDescription,
                    tint = if(showError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black
            ),
            trailingIcon = {
                Icon(icon,"",
                    Modifier
                        .clickable { onExpanded(!isExpanded) })
            }
        )
        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = (-8).dp)
                    .fillMaxWidth(fraction = 0.9f)
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpanded(if (isExpanded) !isExpanded else isExpanded) },
            modifier = Modifier
                .width(with(LocalDensity.current){textSize.width.toDp()})
        )
        {
            services.forEach { label ->
                DropdownMenuItem(onClick = {
                    onValueChange(label.toString())
                    onExpanded(if (isExpanded) !isExpanded else isExpanded)
                }) {
                    Text(text = label.toString())
                }
            }
        }
    }
}

@Composable
fun CustomDatePicker(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    showError: Boolean = false,
    errorMessage: String = "",
    curYear: Int ,
    curMonth: Int ,
    curDay: Int ,
){
    val mContext = LocalContext.current

    var mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

            onValueChange("$mDayOfMonth/${mMonth+1}/$mYear")
        },
        curYear,
        curMonth,
        curDay
    )
    mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            readOnly = true,
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            label = {Text(text = label)},
            leadingIcon = {
                Icon(
                    imageVector = leadingIconImageVector,
                    contentDescription = leadingIconDescription,
                    tint = if(showError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .clickable {
                            mDatePickerDialog.show()
                        }
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black
            )
        )
        if (showError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = (-8).dp)
                    .fillMaxWidth(fraction = 0.9f)
            )
        }
    }
}

@Composable
fun CustomListItem(
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    title1: String = "",
    title2: String = "",
    title3: String = "",

){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = leadingIconDescription,
                tint = MaterialTheme.colors.onSurface
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,

            ) {
                Text(text = title1)
                Text(text = title2)
                Text(text = title3)
            }

            IconButton(
                modifier = Modifier
                    .padding(10.dp),
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = Color.Black
                )
            }

        }

    }
}
