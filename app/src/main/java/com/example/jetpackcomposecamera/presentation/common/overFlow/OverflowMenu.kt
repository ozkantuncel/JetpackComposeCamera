package com.example.jetpackcomposecamera.presentation.common.overFlow

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposecamera.presentation.ui.theme.ColorApp10


@Composable
fun OverflowMenu(showMenu: MutableState<Boolean>, content: @Composable () -> Unit) {


    IconButton(onClick = {
        showMenu.value = !showMenu.value
    }) {
        Icon(
            tint = Color.White,
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
        )
    }
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false }
    ) {
        content()
    }
}

@Composable
fun DropDownItemPager(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {

    DropdownMenuItem(onClick = onClick) {
        Icon(
            tint = ColorApp10,
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = ColorApp10,)
    }
}

