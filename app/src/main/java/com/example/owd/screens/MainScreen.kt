package com.example.owd.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owd.data.Group


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeBody() {
    Scaffold (
        topBar = { CenterAlignedTopAppBar(title = {
            Text(
                text = "Top app bar",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                modifier = Modifier.padding(20.dp)
            )
        }, navigationIcon = {
            TextButton(
                onClick = { },
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp)
            ) {
                Icon(Icons.Default.Menu, "Menu button for displaying settings")
            }
        })
        },

        floatingActionButton = {
        SmallFloatingActionButton(
            onClick = { },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }){

        Column {
            GroupList(
                groupList = listOf(
                    Group(id = 3, name = "Group 2", description = "ewc"),
                    Group(id = 4, name = "Group 3", description = "ewc"),
                    Group(id = 5, name = "Group 4", description = "ewc"),
                    Group(id = 3, name = "Group 2", description = "ewc"),
                    Group(id = 4, name = "Group 3", description = "ewc"),
                    Group(id = 5, name = "Group 4", description = "ewc"),
                    Group(id = 3, name = "Group 2", description = "ewc"),
                    Group(id = 4, name = "Group 3", description = "ewc"),
                    Group(id = 5, name = "Group 4", description = "ewc"),
                    Group(id = 3, name = "Group 2", description = "ewc"),
                    Group(id = 4, name = "Group 3", description = "ewc"),
                    Group(id = 5, name = "Group 4", description = "ewc"),
                    Group(id = 3, name = "Group 2", description = "ewc"),
                    Group(id = 4, name = "Group 3", description = "ewc"),
                    Group(id = 5, name = "Group 4", description = "ewc")
                ),
                onItemClick = {},
                contentPadding = it
            )
        }
    }
}


@Composable
fun GroupList(
    groupList: List<Group>,
    onItemClick: (Group) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (contentPadding = contentPadding, modifier = modifier){
        items(groupList) { group ->
            GroupItem(
                group = group,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { }
            )
        }
    }
}

@Composable
fun GroupItem(
    group: Group, modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = group.description,
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp)
                )
        }
    }
}