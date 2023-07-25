package com.example.jetpackcomposecamera.presentation.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModel
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.Purple40
import com.example.jetpackcomposecamera.util.hawk.Prefs.setStayIn

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val imageList = viewModel.listImage.observeAsState(listOf())

    MainPage(
        navController = navController,
        imageList = imageList,
        deleteImage = {
            viewModel.deleteData(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavController,
    imageList: State<List<ImageModel>>,
    deleteImage: (ImageModel) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),
            actions = {
                IconButton(onClick = {
                    setStayIn(false)
                    navController.popBackStack()
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription =null )
                }
            }
        )
    }, content = { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(imageList.value) { image ->

                    CardImage(image = image) {
                        deleteImage(it)
                    }
                }
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.popBackStack()
            navController.navigate(Screen.CameraView.route)
        }, shape = RoundedCornerShape(50), contentColor = Purple40) {
            Icon(
                painter = painterResource(id = R.drawable.camera_ico),
                contentDescription = "camera_ico"
            )
        }
    }, floatingActionButtonPosition = FabPosition.End)

}


