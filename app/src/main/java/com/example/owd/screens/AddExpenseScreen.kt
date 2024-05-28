package com.example.owd.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.owd.AppViewModelProvider
import com.example.owd.R
import com.example.owd.data.persons.Person
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.AddExpenseViewModel

object AddExpense : NavDest {
    override val route = "add_expense"
    override val screenTitle = R.string.add_group
    const val groupId = "groupId"
    val routeWithArgs = "$route/{$groupId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(viewModel: AddExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    Scaffold (
        topBar = { CenterAlignedTopAppBar(title = {
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
                onClick = {  },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
            ) {
                Icon(Icons.Filled.Check, "Small floating action button.")
            }
        }){innerPadding ->
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
                    value = viewModel.addExpenseUiState.expenseDetails.name,
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
                    value = viewModel.addExpenseUiState.expenseDetails.amount,
                    onValueChange = { viewModel.updateAmount(it)},
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
                    value = viewModel.addExpenseUiState.expenseDetails.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    label = { Text("Expense Description") })

                Text(
                    text = "Members",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )
                LazyColumn {
                    items(viewModel.addExpenseUiState.expenseDetails.personsExpense) { member ->
                        Members(member)
                    }
            }
    }
}
}

@Composable
fun Members(member: Person) {
    Text(
        text = member.name,
        style = MaterialTheme.typography.headlineSmall,
        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
        modifier = Modifier.padding(10.dp)
    )

}