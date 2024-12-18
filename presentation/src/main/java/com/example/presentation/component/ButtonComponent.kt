package com.example.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.theme.Primary_Red_500


@Composable
fun CustomCheckBox(isSelected: Boolean, onValueChanged: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .border(
                width = 1.dp, color = Primary_Red_500
            )
            .background(color = if (isSelected) Primary_Red_500 else Color.Transparent)
            .clickable { onValueChanged() },
    )
}

@Composable
fun CustomRadioBtn(isSelected: Boolean, onValueChanged: () -> Unit) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .border(
                width = 1.dp, color = Primary_Red_500, shape = CircleShape
            )
            .background(color = if (isSelected) Primary_Red_500 else Color.Transparent, shape = CircleShape)
            .clickable { onValueChanged() },
    )
}