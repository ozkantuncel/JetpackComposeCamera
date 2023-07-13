package com.example.jetpackcomposecamera.presentation.main_screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModel
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModelFactory
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.Purple40
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val viewModel: MainScreenViewModel =
        viewModel(factory = MainScreenViewModelFactory(context.applicationContext as Application))

    val imageList = viewModel.listImage.observeAsState(listOf())


    Scaffold(topBar = {
        TopAppBar(
            title = {},
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40)
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
                        viewModel.deleteData(it)
                        File(it.image_dir).delete()
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


