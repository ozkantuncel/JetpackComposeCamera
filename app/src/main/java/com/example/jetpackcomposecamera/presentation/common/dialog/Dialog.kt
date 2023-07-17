package com.example.jetpackcomposecamera.presentation.common.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


@Composable
fun JCCAlertDialog(
    openTheDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    content: @Composable () -> Unit,
    shape: Shape = RoundedCornerShape(10.dp),
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.White,
    titleTextStyle: TextStyle = TextStyle(
        color = Color.Black
    ),
    messageTextStyle: TextStyle = TextStyle(
        color = Color.Black
    ),
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            openTheDialog.value = !openTheDialog.value
        },
        buttons = {
            content()
        },
        properties = properties,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        shape = shape,
        text = {
            message?.let {
                Text(text = message, style = messageTextStyle)
            }
        },
        title = {
            title?.let {
                Text(text = title, style = titleTextStyle)
            }
        }
    )
}
