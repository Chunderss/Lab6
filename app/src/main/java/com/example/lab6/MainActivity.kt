package com.example.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab6.ui.theme.Lab6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyTodo()
                }
            }
        }
    }
}

@Composable
fun MyTodo() {

    var myList by remember { mutableStateOf(listOf("")) }
    var theTempList: MutableList<String> = myList.toMutableList()
    Scaffold(
        Modifier.fillMaxWidth(),
        topBar = { MyTopBar() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyRow { item ->
                theTempList.add(item)
                myList = theTempList
            }
            MyTodoList({ item ->
                theTempList.remove(item)
                myList = theTempList
            }, myList)
        }
    }
}

@Composable
fun MyRow(
    onItemAdded: (String) -> Unit
) {
    var myField by remember { mutableStateOf("") }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TextField(
            value = myField,
            onValueChange = { myField = it },
            label = { Text(text = stringResource(R.string.type_here)) }
        )
        Button(onClick = {
            onItemAdded(myField)
            myField = ""
        }) {
            Text(text = stringResource(R.string.add_item))
        }
    }
}

@Composable
fun MyTodoList(
    onHeld: (String) -> Unit,
    theList: List<String>
) {
    LazyColumn() {
        items(theList.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal =  40.dp, vertical = 10.dp)
                    .clickable { onHeld(theList[index]) },
                elevation = 15.dp
            ) {
                Text(
                    theList[index],
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}


@Composable
fun MyTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.my_todo), color = Color.White) },
        backgroundColor = Color.Blue,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab6Theme {
        MyTodo()
    }
}