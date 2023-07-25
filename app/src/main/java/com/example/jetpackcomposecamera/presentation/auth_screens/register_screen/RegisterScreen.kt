package com.example.jetpackcomposecamera.presentation.auth_screens.register_screen


import android.app.Activity
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.jetpackcomposecamera.presentation.common.text.HyperlinkText
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.util.UiState
import com.example.jetpackcomposecamera.util.hawk.Prefs.setStayIn
import com.example.jetpackcomposecamera.util.hideKeyboard

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity

    val userState = viewModel.user.observeAsState().value

    val errorUserState = remember { mutableStateOf(false) }
    val errorTitle = remember { mutableStateOf("") }

    when (userState) {
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
            userState.error.let {
                errorTitle.value = it.toString()
            }
        }

        is UiState.Empty -> {}

        else -> {}
    }

    RegisterPage(
        errorUserState = errorUserState,
        errorTitle = errorTitle,
        activity = activity,
        onRegisterClick = { userModel ->
            viewModel.insertUser(userModel)
        },
        onClickNav = {
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
    )

}

@Composable
fun RegisterPage(
    errorUserState: MutableState<Boolean>,
    errorTitle: MutableState<String>,
    activity: Activity,
    onRegisterClick: (UserModel) -> Unit,
    onClickNav: () -> Unit

) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordAgain = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val isAllowTerms = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onClickNav) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create account",
                        color = Color.Blue,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.weight(0.05f))

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
                Spacer(modifier = Modifier.weight(0.05f))

                Row(modifier = Modifier.fillMaxWidth()) {

                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                    OutlinedTextField(
                        textStyle = TextStyle(textAlign = TextAlign.Start),
                        value = username.value,
                        onValueChange = {
                            username.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            cursorColor = Color.Green
                        ),
                        placeholder = {
                            Text(
                                text = "Username",
                                fontSize = 15.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        isError = errorUserState.value
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null
                    )

                    OutlinedTextField(
                        textStyle = TextStyle(textAlign = TextAlign.Start),
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            cursorColor = Color.Green
                        ),
                        placeholder = {
                            Text(
                                text = "Password",
                                fontSize = 15.sp
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = if (passwordVisible.value) painterResource(id = R.drawable.opened_eye_ico) else painterResource(
                                    id = R.drawable.closed_eye_ico
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        passwordVisible.value = !passwordVisible.value
                                    }
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = errorUserState.value
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null
                    )

                    OutlinedTextField(
                        textStyle = TextStyle(textAlign = TextAlign.Start),
                        value = passwordAgain.value,
                        onValueChange = {
                            passwordAgain.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            cursorColor = Color.Green
                        ),
                        placeholder = {
                            Text(
                                text = "Password again",
                                fontSize = 15.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardActions = KeyboardActions(onDone = { activity.hideKeyboard() }),
                        isError = errorUserState.value
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = isAllowTerms.value,
                        onClick = {
                            isAllowTerms.value = !isAllowTerms.value
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                    )

                    HyperlinkText(
                        fullText = "I agree to Terms of Use and Privacy Policy.",
                        linkText = listOf("Terms of Use", "Privacy Policy"),
                        hyperlinks = listOf(
                            "https://github.com/ozkantuncel",
                            "https://www.google.com/"
                        )
                    )
                }


                Spacer(modifier = Modifier.weight(0.2f))

                Button(
                    modifier = Modifier.size(width = 250.dp, height = 50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Blue
                    ),
                    onClick = {
                        if(isAllowTerms.value){
                            if (password.value == passwordAgain.value) {
                                onRegisterClick(UserModel(0, username.value, password.value))
                                setStayIn(true)
                            } else {
                                errorUserState.value = true
                                errorTitle.value = "Sifreler aynı olamaz"
                            }
                        }else{
                            errorUserState.value = true
                            errorTitle.value = "Kullanım Koşullarını ve Gizlilik Politikasını kabul edilmedi"
                        }

                    }

                ) {
                    Text(text = "Agree and Register", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.weight(0.2f))
            }

        }

    )

}