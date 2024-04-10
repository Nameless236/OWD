package com.example.owd.screens

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddGroupBackground(modifier: Modifier)
{
    Column {
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 20.dp).fillMaxWidth()){
            Text(text = "Group Name", modifier = Modifier.padding(start = 20.dp),fontSize = 30.sp)
            Spacer(modifier = Modifier.size(30.dp))
            TextField(value = "", onValueChange = {}, modifier = Modifier)
        }
    }
}