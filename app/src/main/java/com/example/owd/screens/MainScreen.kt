package com.example.owd.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddGroupsButton(onClick : () -> Unit)
{
    Row (modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End){
        SmallFloatingActionButton(
            onClick = { onClick()},
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }
}

@Composable
fun CreateBackground()
{
    Row (horizontalArrangement = Arrangement.Start, modifier = Modifier){
        TextButton(
            onClick = { },
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
        ) {
            Icon(Icons.Default.Menu, "Menu button for displaying settings")
        }
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "Groups",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}