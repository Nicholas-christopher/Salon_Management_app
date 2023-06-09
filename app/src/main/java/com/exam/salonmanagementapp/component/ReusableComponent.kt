package com.exam.salonmanagementapp.component

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.util.DisplayMetrics
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.exam.salonmanagementapp.R
import com.exam.salonmanagementapp.Screen
import com.exam.salonmanagementapp.data.Appointment
import com.exam.salonmanagementapp.data.Customer
import com.exam.salonmanagementapp.data.Product


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
                        navController.navigate(route = Screen.Login.route)
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
    leadingIconImageVector2: ImageVector,
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
                    imageVector = leadingIconImageVector2,
                    contentDescription = "",
                    tint = Color.Black
                )
            }

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OwnerBackground(
    navController: NavController,
    content: @Composable ()-> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    /*
    val sharedPreference =  context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE)
    val customerId = sharedPreference.getString("customerId", null)
    if (customerId == null) {
        navController.navigate(route = Screen.CustomerLanding.route)
        return
    }

     */

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { OwnerTopBar(navController = navController) },
        content = { OwnerContent(navController = navController, content = content, scrollState = scrollState ) },
        bottomBar = { OwnerBottomBar(navController = navController) }
    )

    /*

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
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.8f)
                ),
            backgroundColor = Color.Black.copy(alpha = 0.8f)
        ) {
            OwnerBottomNav(navController = navController)
        }
    }

     */
}

@Composable
fun OwnerTopBar(
    navController: NavController,
) {
    TopAppBar(
        title = {
            Text(text = "Hi! Owner")
        },
        actions = {
            // lock icon
            IconButton(onClick = {
                navController.navigate(route = Screen.Login.route)
            }) {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock")
            }
        },
        backgroundColor = Color.Black.copy(alpha = 0.8f),
        contentColor = Color.White
    )
}

@Composable
fun OwnerTopBar(
    navController: NavController,
    title: String,
    navigationIcon: @Composable (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = Color.Black.copy(alpha = 0.8f),
        contentColor = Color.White
    )
}

@Composable
fun OwnerContent(
    navController: NavController,
    scrollState: ScrollState,
    content: @Composable ()-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .paint(
                painter = painterResource(R.drawable.salon_bg2),
                contentScale = ContentScale.Crop
            )
            .background(
                color = Color.White.copy(alpha = 0.9f)
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun OwnerBottomBar(
    navController: NavController,
) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(
        elevation = 10.dp,
        backgroundColor = Color.Black.copy(alpha = 0.8f),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.CalendarMonth,"")
            },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
                navController.navigate(route = Screen.OwnerAppointment.route)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Inventory,"")
            },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
                navController.navigate(route = Screen.OwnerProduct.route)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home,"")
            },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
                navController.navigate(route = Screen.OwnerLanding.route)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Groups,"")
            },
            selected = (selectedIndex.value == 3),
            onClick = {
                selectedIndex.value = 3
                navController.navigate(route = Screen.OwnerCustomer.route)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Person, "")
            },
            selected = (selectedIndex.value == 4),
            onClick = {
                selectedIndex.value = 4
            }
        )
    }
}


@Composable
fun CustomProducts(
    navController: NavController,
    products: List<Product>
){
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val dpWidth = (displayMetrics.widthPixels / displayMetrics.density) / 2 - 40

    val itemPerRow = 2
    val rows = (products.size / itemPerRow)
    System.out.println("rows => $rows")
    for (i in 0..rows) {
        System.out.println("column => ${i}")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0..itemPerRow-1) {
                    System.out.println("row => ${j}")
                    System.out.println("index => ${i*itemPerRow+j}")
                    if (i*itemPerRow+j < products.size-1) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(route = Screen.OwnerProductDetail.passId(products[i*itemPerRow+j].id))
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(data = products[i*itemPerRow+j].productImage)
                                        .build()
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(dpWidth.dp)
                                    .border(
                                        border = BorderStroke(1.dp, Color.DarkGray),
                                        shape = RoundedCornerShape(15.dp)
                                    ),
                                contentScale = ContentScale.Crop
                            )
                            Text(text = products[i*itemPerRow+j].productName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomAppointments(
    navController: NavController,
    appointments: List<Appointment>,
){
    if (appointments.isNotEmpty()) {
        appointments.map {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = Color.LightGray
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                navController.navigate(route = Screen.OwnerAppointmentDetail.passId(it.appointmentId))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Update,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Text(text = it.appointmentDate + " " + it.appointmentTime)
                            Text(text = it.service)
                        }

                        IconButton(
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
        }
    }
}


@Composable
fun CustomCustomerDetails(
    navController: NavController,
    customer: Customer
    ){
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp))
            .fillMaxWidth(),
        backgroundColor = Color.LightGray
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(text = "Name: " + customer.name)
                Text(text = "Phone: " + customer.phone)
                Text(text = "Email: " + customer.email)
            }

            IconButton(
                onClick = {
                    navController.navigate(route = Screen.OwnerCustomerDetail.passId(customer.id))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Wysiwyg,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
    }
}
