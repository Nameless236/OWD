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
import com.example.owd.viewModels.AddExpenseUiState
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add Expense",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 40.sp,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            )
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
        },
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)) {
            item {
                AddExpenseForm(viewModel,uiState.members, viewModel.addExpenseUiState, expanded) { expanded = it }
            }
            items(uiState.members) { member ->
                val isSelected = selectedMembers.contains(member)
                Members(member, isSelected) { isChecked ->
                    selectedMembers = if (isChecked) selectedMembers + member else selectedMembers - member
                    viewModel.updateSelectedMembers(selectedMembers)
                }
            }
        }
    }
}

@Composable
fun AddExpenseForm(viewModel: GroupDetailsViewModel,members:List<Person>, uiState: AddExpenseUiState, expanded: Boolean, onExpandedChange: (Boolean) -> Unit)
{
    ExpenseFormField("Title", uiState.expenseDetails.name) { viewModel.updateTitle(it) }
    ExpenseFormField("Amount", uiState.expenseDetails.amount) { viewModel.updateAmount(it) }
    Text(
        text = "Paid by",
        style = MaterialTheme.typography.headlineSmall,
        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
        modifier = Modifier.padding(10.dp)
    )
    MemberSelection(viewModel, members, expanded, onExpandedChange)

    Text(
        text = "Members",
        style = MaterialTheme.typography.headlineSmall,
        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
        modifier = Modifier.padding(10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberSelection(viewModel: GroupDetailsViewModel,members: List<Person>, expanded: Boolean, onExpandedChange: (Boolean) -> Unit)
{
    var selectedMember by remember { mutableStateOf<Person?>(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandedChange) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedMember?.name ?: "Select payer",
                onValueChange = {},
                readOnly = true
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
                members.forEach { member ->
                    DropdownMenuItem(
                        text = { Text(text = member.name) },
                        onClick = {
                            selectedMember = member
                            viewModel.updatePayer(member)
                            onExpandedChange(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseFormField(label: String, value: String, onValueChange: (String) -> Unit)
{
    Text(
        text = label,
        style = MaterialTheme.typography.headlineSmall,
        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
        modifier = Modifier.padding(10.dp)
    )
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        label = { Text(label) }
    )
}

@Composable
fun Members(member: Person, isSelected: Boolean, onSelectionChange: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(isSelected) }
    Card(modifier = Modifier, shape = RectangleShape) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Row(modifier = Modifier) {
                Checkbox(checked = checked, onCheckedChange = {
                    checked = it
                    onSelectionChange(checked)
                })
                Text(
                    text = member.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.8f)
                )
            }
        }
    }
}
