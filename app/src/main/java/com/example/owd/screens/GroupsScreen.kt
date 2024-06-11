package com.example.owd.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.owd.AppViewModelProvider
import com.example.owd.R
import com.example.owd.data.groups.Group
import com.example.owd.navigation.NavDest
import com.example.owd.viewModels.GroupsViewModel

/**
 * Represents the destination for the Groups screen.
 */
object GroupsDest : NavDest{
    override val route = R.string.groups_route.toString()
    override val screenTitle = R.string.groups
}

/**
 * Composable function for displaying the main Groups screen.
 * @param navigateToAddGroup Function to navigate to the Add Group screen.
 * @param navigateToGroupDetails Function to navigate to the Group Details screen.
 * @param modifier Modifier for the screen.
 * @param viewModel ViewModel for managing Groups data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigateToAddGroup: () -> Unit,
    navigateToGroupDetails: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GroupsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val groupsState by viewModel.groupsUIState.collectAsState()
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.groups),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                        fontSize = 40.sp,
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = navigateToAddGroup,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_group))
            }
        }
    ) {
            innerPadding ->
        GroupsBody(groupList = groupsState.groupList, contentPadding = innerPadding, onItemClick = navigateToGroupDetails)

    }
}

/**
 * Composable function for displaying the body of the Groups screen.
 * @param groupList List of groups to display.
 * @param onItemClick Function to handle item click events.
 * @param contentPadding Padding values for the content.
 */
@Composable
fun GroupsBody(
    groupList: List<Group>,
    onItemClick: (Long) -> Unit,
    contentPadding: PaddingValues
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (groupList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_groups),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            GroupList(
                groupList = groupList,
                onItemClick = onItemClick,
                contentPadding = contentPadding
            )
        }
    }
}

/**
 * Composable function for displaying the list of groups.
 * @param groupList List of groups to display.
 * @param onItemClick Function to handle item click events.
 * @param contentPadding Padding values for the content.
 * @param modifier Modifier for the list.
 */
@Composable
fun GroupList(
    groupList: List<Group>,
    onItemClick: (Long) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (contentPadding = contentPadding, modifier = modifier){
        items(items = groupList, key = { it.id }) { group ->
            GroupItem(
                group = group,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onItemClick(group.id) }
            )
        }
    }
}

/**
 * Composable function for displaying a single group item.
 * @param group Group to display.
 * @param modifier Modifier for the group item.
 */
@Composable
fun GroupItem(
    group: Group,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
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
