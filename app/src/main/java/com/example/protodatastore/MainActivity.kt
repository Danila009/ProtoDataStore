package com.example.protodatastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.protodatastore.proto.PersonViewModel
import com.example.protodatastore.ui.theme.ProtoDataStoreTheme

class MainActivity : ComponentActivity() {

    private lateinit var personViewModel:PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        personViewModel = ViewModelProvider(this)[PersonViewModel::class.java]

        setContent {
            val usernameProto = remember { mutableStateOf("") }
            val ageProto = remember { mutableStateOf(0) }

            val username = remember { mutableStateOf("") }

            personViewModel.readPersonProto.observe(this) {
                usernameProto.value = it.username
                ageProto.value = it.age
            }

            ProtoDataStoreTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Username: ${usernameProto.value}")
                    Text(text = "Age: ${ageProto.value}")

                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = { Text(text = "Username")}
                    )

                    OutlinedButton(onClick = {
                        personViewModel.updatePerson(
                            username = username.value,
                            age = (1..100).random()
                        )
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}