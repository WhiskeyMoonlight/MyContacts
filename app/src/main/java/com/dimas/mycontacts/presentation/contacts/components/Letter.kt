package com.dimas.mycontacts.presentation.contacts.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Letter(
    letter: Char,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = letter.toString())
        Spacer(modifier = Modifier.weight(1f))
    }
}