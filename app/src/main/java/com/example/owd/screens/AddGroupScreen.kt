package com.example.owd.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Group", modifier = Modifier.padding(20.dp), fontSize = 40.sp, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.headlineLarge)
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(top = 20.dp))
        {
            Text(text = "Group Name", modifier = Modifier.padding(start = 20.dp),fontSize = 25.sp)
            Spacer(modifier = Modifier.size(30.dp))
            TextField(value = "", onValueChange = {}, modifier = Modifier)
        }
        Row (modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Description", modifier = Modifier.padding(start = 20.dp),fontSize = 25.sp)
            Spacer(modifier = Modifier.size(30.dp))
            TextField(value = "", onValueChange = {}, modifier = Modifier)
        }
    }
}