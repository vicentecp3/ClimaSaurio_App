package com.composeweatherapp.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ForecastTitle(text: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.h2.copy(fontSize = 20.sp),
        textAlign = TextAlign.Center // Centra el texto horizontalmente
    )
}