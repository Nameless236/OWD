package com.example.owd.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owd.R
import com.example.owd.data.persons.Person
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.GroupDetailsViewModel
import kotlinx.coroutines.launch

object AddExpenseDest : NavDest {
    override val route = "add_expense"
    override val screenTitle = R.string.add_group
    const val groupId = "groupId"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(viewModel: GroupDetailsViewModel, navigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var selectedMembers by remember { mutableStateOf<List<Person>>(emptyList()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Add Expense",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 40.sp,
                    fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    modifier = Modifier.padding(20.dp)
                )
            })
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveExpense()
                    }
                    navigateBack()
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
        ) {
            Text(
                text = "Title",
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = viewModel._addExpenseUiState.expenseDetails.name,
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                label = { Text("Title") })

            Text(
                text = "Amount",
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = viewModel._addExpenseUiState.expenseDetails.amount,
                onValueChange = { viewModel.updateAmount(it) },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                label = { Text("Amount") })

            Text(
                text = "Description",
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = viewModel._addExpenseUiState.expenseDetails.description,
                onValueChange = { viewModel.updateDescription(it) },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                label = { Text("Expense Description") })

            Text(
                text = "Paid by",
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                modifier = Modifier.padding(10.dp)
            )
            MemberSelection(viewModel, uiState.members, expanded, { expanded = it }) {
                expanded = false
            }

            Text(
                text = "Members",
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                modifier = Modifier.padding(10.dp)
            )
            LazyColumn {
                val updatedList = selectedMembers.toMutableList()
                items(uiState.members) { member ->
                    Members(member) { isChecked ->
                        if (isChecked) {
                            updatedList.add(member)
                        } else {
                            updatedList.remove(member)
                        }
                        selectedMembers = updatedList
                        viewModel.updateSelectedMembers(selectedMembers)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberSelection(viewModel: GroupDetailsViewModel,members: List<Person>, expanded: Boolean, onExpandedChange: (Boolean) -> Unit, onDismissChange: () -> Unit) {

    var selectedMember by remember { mutableStateOf<Person?>(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange(it) }) {

            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedMember?.name ?: "Select payer",
                onValueChange = {},
                readOnly = true
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onDismissChange() }) {
                members.forEach { member ->
                    DropdownMenuItem(
                        text = { Text(text = member.name) },
                        onClick = {
                            selectedMember = member
                            viewModel.updatePayer(member)
                            onDismissChange()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun Members(member: Person, onSelectionChange: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(true) }
    Card(modifier = Modifier, shape = RectangleShape) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Row(modifier = Modifier) {
                Text(
                    text = member.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.8f)
                )
                Checkbox(checked = checked, onCheckedChange = {
                    checked = !checked
                    onSelectionChange(checked)
                })
            }
        }
    }
}
