package com.example.owd.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import com.example.owd.R
import com.example.owd.data.expenses.Expense
import com.example.owd.data.persons.Person
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.GroupDetailsViewModel
import kotlinx.coroutines.launch

object GroupDetailsDest : NavDest {
    override val route = "group_details"
    override val screenTitle = R.string.add_group
    const val groupId = "groupId"
    val routeWithArgs = "$route/{$groupId}"
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailScreen(
    navigateToAddExpense: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: GroupDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var displayChart by remember { mutableStateOf(viewModel.displayBalances) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(
                    title = { Text(
                        text = uiState.group.name,
                        modifier = Modifier.padding(20.dp),
                        fontSize = 40.sp,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                    actions = {
                        IconButton(onClick = { menuExpanded = !menuExpanded }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "More")
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Group") },
                                onClick = {
                                    menuExpanded = false
                                    coroutineScope.launch {
                                        viewModel.deleteGroup()
                                    }
                                    navigateToHome()
                                }
                            )
                        }
                    })
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = { displayChart = false }) {
                        Text(
                            "Expenses",
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                            fontSize = 20.sp
                        )
                    }
                    TextButton(onClick = { displayChart = true }) {
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
        if (displayChart) {
            viewModel.updateAmounts()
            val list = viewModel._addExpenseUiState.amounts
            if (list.isEmpty())
                Text("No expenses yet")
            else
                DisplayGraph(list, it)

        } else {
            ExpenseScreen(it, viewModel)
        }
    }
}

@Composable
fun ExpenseScreen(contentPaddingValues: PaddingValues, viewModel: GroupDetailsViewModel)
{
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn (contentPadding = contentPaddingValues) {
        items(uiState.expensesList) { expense ->
            val paidBy = uiState.members.find { it.id == expense.paidBy }?.name ?: ""
            DisplayExpenses(expense, paidBy)
        }
    }
}

@Composable
fun DisplayGraph(data: List<Pair<Person, Float>>, paddingValues: PaddingValues)
{
    val heightPerMember = 50.dp
    val graphHeight = (data.size * heightPerMember).coerceAtLeast(200.dp)

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
        contentAlignment = Alignment.TopCenter) {
        val pointList = data.mapIndexed { index, pair -> Point(pair.second, index.toFloat()) }

        val barData = pointList.mapIndexed { index, point ->
            BarData(
                point = point,
                label = data[index].first.name,
                color = if (point.x < 0) Color.Red else Color.Green
            )
        }

        val xAxisData = AxisData.Builder()
            .steps(5)
            .bottomPadding(12.dp)
            .endPadding(4.dp)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .build()

        val yAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .steps(barData.size - 1)
            .labelAndAxisLinePadding(20.dp)
            .axisLineColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .axisLineThickness(0.dp)
            .axisOffset(30.dp)
            .startDrawPadding(40.dp)
            .shouldDrawAxisLineTillEnd(false)
            .labelData { index ->
                """ ${data[index].first.name} 
                   ${Math.round(data[index].second * 100.0) / 100.0}€
                   """ }
            .setDataCategoryOptions(
                DataCategoryOptions(
                    isDataCategoryInYAxis = true,
                    isDataCategoryStartFromBottom = true
                )
            )
            .build()

        val barChartData = BarChartData(
            chartData = barData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            backgroundColor = MaterialTheme.colorScheme.surface,
            barStyle = BarStyle(
                isGradientEnabled = false,
                paddingBetweenBars = 20.dp,
                barWidth = 35.dp,
                selectionHighlightData = SelectionHighlightData(
                    highlightBarColor = Color.Red,
                    highlightTextColor = MaterialTheme.colorScheme.tertiary,
                    highlightTextBackgroundColor = MaterialTheme.colorScheme.surface,
                    popUpLabel = { x, _ -> " Owes : $x " },
                    barChartType = BarChartType.HORIZONTAL
                ),
            ),
            showYAxis = true,
            showXAxis = false,
            horizontalExtraSpace = 30.dp,
            barChartType = BarChartType.HORIZONTAL
        )

        Box(
            modifier = Modifier
                .height(graphHeight),
            contentAlignment = Alignment.TopCenter
        ) {
            BarChart(
                modifier = Modifier.fillMaxWidth(),
                barChartData = barChartData
            )
        }
    }
}


@Composable
fun DisplayExpenses(expense: Expense, paidBy: String)
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
                    text = "Paid by " + paidBy,
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