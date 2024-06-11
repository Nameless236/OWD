package com.example.owd.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.owd.AppViewModelProvider
import com.example.owd.R
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.AddGroupViewModel
import kotlinx.coroutines.launch

object AddGroup : NavDest {
    override val route = "add_group"
    override val screenTitle = R.string.add_group
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupBackground(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddGroupViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.add_group),
                    modifier = Modifier.padding(20.dp),
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge
                )
            })
        },
        modifier = modifier,
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                        navigateBack()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
            ) {
                Icon(Icons.Filled.Check, "Small floating action button.")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Text(
                    text = "Group Name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )
                TextField(
                    value = viewModel.groupUIState.groupDetails.name,
                    onValueChange = { viewModel.updateName(it) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    label = { Text("Group Name") },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                Text(
                    text = "Group description",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )

                TextField(
                    value = viewModel.groupUIState.groupDetails.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    label = { Text("Group Description") },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
                )

                Text(
                    text = "Members",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp)
                )
            }

            items(viewModel.groupUIState.groupDetails.members) { member ->
                Members(member)
            }

            item {
                Card(modifier = Modifier.padding(10.dp), shape = RectangleShape) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.addMember() }) {
                                Icon(Icons.Filled.Add, "Add member")
                            }
                            TextField(
                                value = viewModel.newMemberName,
                                onValueChange = { viewModel.newMemberName = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 10.dp),
                                label = { Text("Add member") },
                                textStyle = MaterialTheme.typography.headlineSmall,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = {
                                    coroutineScope.launch {
                                        viewModel.addMember()
                                    }
                                })
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Members(member: String) {
    var checked by remember { mutableStateOf(true) }
    Card(modifier = Modifier.padding(10.dp), shape = RectangleShape) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = member,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
