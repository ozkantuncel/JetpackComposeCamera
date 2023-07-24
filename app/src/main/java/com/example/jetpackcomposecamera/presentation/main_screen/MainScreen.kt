package com.example.jetpackcomposecamera.presentation.main_screen


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposecamera.R
import com.example.jetpackcomposecamera.data.model.ImageModel
import com.example.jetpackcomposecamera.presentation.common.box.JCCBox
import com.example.jetpackcomposecamera.presentation.common.dialog.JCCAlertDialog
import com.example.jetpackcomposecamera.presentation.common.overFlow.DropDownItemPager
import com.example.jetpackcomposecamera.presentation.common.overFlow.OverflowMenu
import com.example.jetpackcomposecamera.presentation.main_screen.viewmodel.MainScreenViewModel
import com.example.jetpackcomposecamera.presentation.navigation.Screen
import com.example.jetpackcomposecamera.presentation.ui.theme.Purple40
import com.example.jetpackcomposecamera.util.mkDir
import com.example.jetpackcomposecamera.util.toast
import java.lang.Exception

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val showMenu = remember {
        mutableStateOf(false)
    }

    // Bir hata iletişim kutusunun durumunu tutmak için durum nesneleri
    val errorDialogState = remember { mutableStateOf(false) }
    val errorTitle = remember { mutableStateOf("") }
    val errorMsg = remember { mutableStateOf("") }


    val imageList = viewModel.listImage.observeAsState(listOf())

    MainPage(
        navController = navController,
        imageList = imageList,
        showMenu = showMenu,
        deleteImage = {
            viewModel.deleteData(it)
        },
        onClickDeleteButton = {
            deleteAllPictures(
                context = context,
                viewModel = viewModel,
                errorState = errorDialogState,
                errorTitle = errorTitle,
                errorMsg = errorMsg
            )
        }
    )
    // Eğer errorDialogState değeri true ise, bir Custom bir Alert Dialog gösterir.
    if (errorDialogState.value) {
        JCCAlertDialog(
            openTheDialog = errorDialogState,
            content = {},
            title = errorTitle.value,
            message = errorMsg.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavController,
    imageList: State<List<ImageModel>>,
    showMenu: MutableState<Boolean>,
    deleteImage: (ImageModel) -> Unit,
    onClickDeleteButton: () -> Unit
) {
    // Genel olarak, Scaffold, bir ekranın veya sayfanın iskeletini oluşturmak için kullanılır.
    // Material Design bileşenleri için yuvalar sağlar.
    // Scaffold, bir Material Design uygulamasının temel yapısını uygulamak için kullanılırken, Surface bir Material Design yüzeyini temsil etmek için kullanılır.
    Scaffold(topBar = {
        //TopAppBar: Sayfanın üst kısmında yer alan bir navigasyon çubuğu.
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = {
                    //TODO()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    //TODO()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = null
                    )
                }
                OverflowMenu(showMenu = showMenu) {

                    DropDownItemPager(
                        imageVector = Icons.Outlined.Delete,
                        text = "Delete All"
                    ) {
                        onClickDeleteButton()
                        showMenu.value = false
                    }

                    DropDownItemPager(
                        imageVector = Icons.Outlined.Warning,
                        text = "Log out"
                    ) {
                        //TODO()
                    }
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40)
        )
    }, content = { padding ->

        // padding.calculateBottomPadding() LazyColumn için düşün
        // Bir Column bileşeni, dikey bir düzen oluşturmak için kullanılır
        Column(
            modifier = Modifier.padding(padding)
        ) {


            // Box bileşeni, içine eklenen bileşenleri bir kutu içerisinde yerleştirir.
            // Bileşenleri daha serbest bir şekilde yerleştirir ve bileşenleri istediğiniz şekilde hizalayabiliriz.
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(imageList.value) { index, image ->
                        CardImage(image = image) {
                            deleteImage(it)
                        }
                        //  // Her 3 öğeden sonra bir JCCBox bileşeni görünür
                        if ((index + 1) % 3 == 0 && index != imageList.value.lastIndex) {
                            JCCBox()
                        }
                    }
                }
            }
        }
    },
        floatingActionButton = {
            //FloatingActionButton: Sayfanın üstünde veya altında yer alan bir kayan eylem düğmesi.
            FloatingActionButton(onClick = {
                navController.popBackStack()
                navController.navigate(Screen.CameraView.route)
            }, shape = RoundedCornerShape(50), contentColor = Purple40) {
                Icon(
                    painter = painterResource(id = R.drawable.camera_ico),
                    contentDescription = "camera_ico"
                )
            }
        }, floatingActionButtonPosition = FabPosition.End
    )

}

fun deleteAllPictures(
    context: Context,
    viewModel: MainScreenViewModel,
    errorState: MutableState<Boolean>,
    errorTitle: MutableState<String>,
    errorMsg: MutableState<String>
) {
    try {
        val directory = context.mkDir()

        val files = directory.listFiles()

        if (files?.isNotEmpty() == true) {
            viewModel.deleteAll(directory)
            context.toast("All pictures deleted")
        } else {
            errorState.value = true
            errorTitle.value = "No Image"
            errorMsg.value = "Image not added"
        }

    } catch (e: Exception) {
        errorState.value = true
        errorTitle.value = e.message.toString()
        errorMsg.value = e.localizedMessage?.toString() ?: "Unknown error"
    }
}


