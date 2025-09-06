package com.graphql.country_api.presentation.ui.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login Screen")
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFE91E63)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
