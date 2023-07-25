package com.example.jetpackcomposecamera.presentation.auth_screens.login_screen

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.UserModel
import com.example.jetpackcomposecamera.presentation.auth_screens.login_screen.viewmodel.LoginScreenViewModel
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.Color2App10
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp10
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp30
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp60
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorWhiteGray
import com.example.jetpackcomposecamera.presentation.ui.theme.PurpleGrey40
import com.example.jetpackcomposecamera.util.UiState
import com.example.jetpackcomposecamera.util.hawk.Prefs.setStayIn
import com.example.jetpackcomposecamera.util.hideKeyboard

@Composable
fun LoginScreen(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
) {

    val activity = LocalContext.current as Activity

    val uiState = loginScreenViewModel.user.observeAsState().value

    val errorUserState = remember { mutableStateOf(false) }
    val errorTitle = remember { mutableStateOf("") }


    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            errorUserState.value = false
            LaunchedEffect(true) {
                navController.popBackStack()
                navController.navigate(Screen.MainScreen.route)
            }
        }

        is UiState.Failure -> {
            errorUserState.value = true
            uiState.error.let {
                errorTitle.value = it.toString()
            }
        }

        is UiState.Empty -> {}

        else -> {}
    }

    LoginScreenPage(
        activity = activity,
        errorUserState = errorUserState,
        errorTitle = errorTitle,
        onLoginClick = {
            loginScreenViewModel.fetchUser(it.user_name, it.user_password)
        },
        onRegisterClick = {
            navController.popBackStack()
            navController.navigate(Screen.RegisterScreen.route)
        }
    )
}

@Composable
fun LoginScreenPage(
    activity: Activity,
    errorUserState: MutableState<Boolean>,
    errorTitle: MutableState<String>,
    onLoginClick: (UserModel) -> Unit,
    onRegisterClick: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val forgotUser = remember { mutableStateOf("") }
    val rememberMeState = remember { mutableStateOf(false) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorApp60)
                .weight(0.3f)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Sign in to your Account",
                    color = ColorWhiteGray,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Sign in to your Account",
                    color = ColorWhiteGray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }


        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxSize()
                .weight(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(0.10f))


            if (errorUserState.value) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    border = BorderStroke(2.dp, Color.Red),
                    content = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (if (errorTitle.value == "null") "Kulanici bilgileri yanlıs" else errorTitle.value),
                                fontSize = 15.sp
                            )
                        }
                    }
                )
            }

            OutlinedTextField(
                textStyle = TextStyle(textAlign = TextAlign.Start),
                value = username.value,
                onValueChange = {
                    username.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (errorUserState.value) Color.Red else ColorApp10,
                    cursorColor = if (errorUserState.value) Color.Red else Color2App10,
                ),
                placeholder = {
                    Text(
                        text = "Rick",
                        fontSize = 15.sp
                    )
                },
                label = {
                    Text(
                        text = "Username",
                        color = PurpleGrey40
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                isError = errorUserState.value,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.weight(0.05f))
            OutlinedTextField(
                textStyle = TextStyle(textAlign = TextAlign.Start),
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (errorUserState.value) Color.Red else ColorApp10,
                    cursorColor = if (errorUserState.value) Color.Red else Color2App10,
                ),
                placeholder = {
                    Text(
                        text = "********",
                        fontSize = 15.sp
                    )
                },
                label = {
                    Text(
                        text = "Password",
                        color = PurpleGrey40
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = if (passwordVisible.value) painterResource(id = R.drawable.opened_eye_ico) else painterResource(
                            id = R.drawable.closed_eye_ico
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                passwordVisible.value = !passwordVisible.value
                            }
                    )
                },
                isError = errorUserState.value,
                keyboardActions = KeyboardActions(onDone = { activity.hideKeyboard() })
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = rememberMeState.value,
                        onClick = {
                            rememberMeState.value = !rememberMeState.value
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = ColorApp30)
                    )

                    Text(
                        text = "Stay signed in",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                rememberMeState.value = !rememberMeState.value
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.10f))

            Button(
                modifier = Modifier.size(width = 300.dp, height = 50.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = ColorApp30
                ),
                onClick = {
                    onLoginClick(UserModel(0, username.value, password.value))
                    setStayIn(rememberMeState.value)
                }

            ) {
                Text(text = "Login", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account",
                    fontSize = 16.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp
                        )
                        .clickable {
                            onRegisterClick()
                        },
                    text = "Register",
                    color = ColorApp30,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}