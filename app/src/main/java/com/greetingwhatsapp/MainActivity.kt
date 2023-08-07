package com.greetingwhatsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greetingwhatsapp.ui.theme.GreetingWhatsappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingWhatsappTheme {
                Alert {
                    startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                }
                Greeting("Android")
            }
        }
    }
}

@Composable
fun Alert(onClick: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(backgroundColor = colorResource(id = R.color.alert),
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Notification Listener Permission")
            },
            text = {
                Text(
                    "This service need Notification permission"
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onClick()
                            openDialog.value = false
                        }
                    ) {
                        Text("Ok")
                    }
                }
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    var contactName by rememberSaveable { mutableStateOf("") }
    var textInclude by rememberSaveable { mutableStateOf("") }
    var greeting by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Greeter Veev App",
            color = colorResource(id = R.color.green_background),
            fontSize = 40.sp,
            modifier = Modifier
                .padding(top = 40.dp)
        )

        Column(modifier = Modifier.padding(bottom = 30.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 20.dp),
                    colors = TextFieldDefaults.textFieldColors(textColor = colorResource(id = R.color.text_color)),
                    label = { Text(text = "Contact Name") },
                    value = contactName,
                    onValueChange = { contactName = it })
                Button(onClick = {
                    LocalManager.contactName.add(contactName)
                    contactName = ""
                }) {
                    Text(text = "Add")
                }
            }


            OutlinedTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                colors = TextFieldDefaults.textFieldColors(textColor = colorResource(id = R.color.text_color)),
                label = { Text(text = "Text Should Include") },
                value = textInclude,
                onValueChange = {
                    textInclude = it
                    LocalManager.textInclude = it
                })

            OutlinedTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                colors = TextFieldDefaults.textFieldColors(textColor = colorResource(id = R.color.text_color)),
                label = { Text(text = "Text To Send") },
                value = greeting,
                onValueChange = {
                    greeting = it
                    LocalManager.greeting = it
                })
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Greeting("Android")
    }
}