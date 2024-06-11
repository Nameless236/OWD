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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object AddGroup : NavDest {
    override val route = R.string.add_group_route.toString()
    override val screenTitle = R.string.add_group
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddGroupViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { AddGroupTopBar() },
        floatingActionButton = { SaveGroupButton(viewModel, navigateBack, coroutineScope) },
        modifier = modifier
    ) { innerPadding ->
        GroupDetailsForm(viewModel, coroutineScope, Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.add_group),
                modifier = Modifier.padding(20.dp),
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    )
}

@Composable
fun SaveGroupButton(viewModel: AddGroupViewModel, navigateBack: () -> Unit, coroutineScope: CoroutineScope) {
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
        Icon(Icons.Filled.Check, stringResource(id = R.string.add_group))
    }
}

@Composable
fun GroupDetailsForm(viewModel: AddGroupViewModel, coroutineScope: CoroutineScope, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            GroupDetailsTextField(
                label = stringResource(id = R.string.group_name),
                value = viewModel.groupUIState.groupDetails.name,
                onValueChange = viewModel::updateName
            )
            GroupDetailsTextField(
                label = stringResource(id = R.string.group_description),
                value = viewModel.groupUIState.groupDetails.description,
                onValueChange = viewModel::updateDescription
            )
            Text(
                text = stringResource(id = R.string.members),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(10.dp)
            )
        }

        items(viewModel.groupUIState.groupDetails.members) { member ->
            Members(member)
        }

        item {
            NewMemberCard(viewModel, coroutineScope)
        }
    }
}

@Composable
fun GroupDetailsTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            textStyle = MaterialTheme.typography.headlineSmall,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
    }
}

@Composable
fun NewMemberCard(viewModel: AddGroupViewModel, coroutineScope: CoroutineScope) {
    Card(modifier = Modifier.padding(10.dp), shape = RectangleShape) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.addMember() }) {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.add_member))
                }
                TextField(
                    value = viewModel.newMemberName,
                    onValueChange = { viewModel.newMemberName = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 10.dp),
                    label = { Text(stringResource(id = R.string.add_member)) },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
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
