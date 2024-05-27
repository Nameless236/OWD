package com.example.owd.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owd.R
import com.example.owd.data.expenses.Expense
import com.example.owd.navigation.NavDest

object Expenses : NavDest {
    override val route = "expenses"
    override val screenTitle = R.string.add_group
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// expensesScreen(group: Group) {
fun ExpensesScreen() {
    //val expensesList = listOf(Expense(1, "Ahoj", 25.1, 1, 2), Expense(1, "Ahoj", 25.1, 1, 2), Expense(1, "Ahoj", 25.1, 1, 2))
    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = "group.name",
                        modifier = Modifier.padding(20.dp),
                        fontSize = 40.sp,
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
        }
    ) {
        LazyColumn (contentPadding = it) {
//            items(expensesList) { expense ->
//                DisplayExpenses(expense)
//            }
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