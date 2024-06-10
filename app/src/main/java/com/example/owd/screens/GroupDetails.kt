package com.example.owd.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owd.R
import com.example.owd.data.expenses.Expense
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.GroupDetailsViewModel

object GroupDetailsDest : NavDest {
    override val route = "group_details"
    override val screenTitle = R.string.add_group
    const val groupId = "groupId"
    val routeWithArgs = "$route/{$groupId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navigateToAddExpense: () -> Unit,
    viewModel: GroupDetailsViewModel// = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = uiState.group.name,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 40.sp,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                })
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            "Expenses",
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                            fontSize = 20.sp
                        )
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            "Balances",
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                            fontSize = 20.sp
                        )
                    }
                }
            }

        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = navigateToAddExpense,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }
    ) {
        LazyColumn (contentPadding = it) {
            items(uiState.expensesList) { expense ->
                DisplayExpenses(expense)
            }
        }
    }
}

@Composable
fun DisplayExpenses(expense: Expense)
{
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = expense.name,
                style = MaterialTheme.typography.titleLarge,
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(1f))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Paid by " + expense.paidBy.toString(),
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = expense.amount.toString() + "€",
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp),

                )

            }
        }
    }

}