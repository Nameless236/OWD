package com.example.owd.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owd.R
import com.example.owd.data.persons.Person
import com.example.owd.navigation.NavDest

object AddGroup : NavDest {
    override val route = "add_group"
    override val screenTitle = R.string.add_group
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupBackground(modifier: Modifier)
{
    var text by rememberSaveable { mutableStateOf("") }
    var newMemberName by remember { mutableStateOf("") }
    var memberList by remember { mutableStateOf(listOf(Person(1 ,"Jane", 1))) }
//    //val scrollState = rememberScrollState()
//    Column (modifier= modifier.verticalScroll(rememberScrollState())){
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
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(it.calculateTopPadding())
                    .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            ) {
                Text(
                    text = "Group name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )
                TextField(
                    value = text,
                    onValueChange = { it -> text = it },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    label = { Text("Group Name") })

                Text(
                    text = "Group description",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )

                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    label = { Text("Group Description") })

                Text(
                    text = "Members",
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(10.dp)
                )
                LazyColumn {
                    items(memberList) { member ->
                        Members(member)
                    }
                    item {
                        Card(modifier = Modifier, shape = RectangleShape) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                                    TextField(
                                        value = newMemberName,
                                        onValueChange = { newMemberName = it },
                                        modifier = Modifier
                                            .padding(bottom = 10.dp)
                                            .fillMaxWidth(0.8f),
                                        label = { Text("Add member") })
                                    IconButton(onClick = {
                                        memberList = memberList + Person(1, newMemberName, 1)
                                        newMemberName = ""
                                    }) {
                                        Icon(Icons.Filled.Add, "Add member")
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}

@Composable
fun Members(member: Person)
{
    var checked by remember { mutableStateOf(true) }
    Card (modifier = Modifier, shape = RectangleShape) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Row(modifier = Modifier) {
                Text(
                    text = member.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.8f)
                )
                Checkbox(checked = checked, onCheckedChange = {})
            }
        }
    }
}