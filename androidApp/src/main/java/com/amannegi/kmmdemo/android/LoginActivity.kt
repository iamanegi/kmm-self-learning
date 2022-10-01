package com.amannegi.kmmdemo.android

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amannegi.kmmdemo.Auth
import com.amannegi.kmmdemo.KtorHelper
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen() {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val ctx = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                Text(
                    text = "Login Screen",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            // email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = "email") }
            )

            // password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_24),
                        contentDescription = "password"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(
                                id = if (showPassword) R.drawable.ic_visibility_24 else R.drawable.ic_visibility_off_24
                            ),
                            contentDescription = "password visibility toggle"
                        )
                    }
                }
            )

            // login button
            Button(
                onClick = {
                    keyboardController?.hide()
                    login(email, password) { message ->
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState
                                .showSnackbar(message = message)
                        }
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.elevation()
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

        }
    }
}

private fun login(email: String, password: String, action: (String) -> Unit) {
    val auth = Auth()
    val (isAuthenticated, message) = auth.authenticateUser(email, password)

    if (isAuthenticated) {
        action(message)
    } else {
        action(message)
    }

    MainScope().launch {
        val products = KtorHelper().getProducts()
        Log.d("LoginScreen", "product: ${products?.products?.get(0)}")
    }
}

@Preview
@Composable
fun DefaultLoginPreview() {
    MyApplicationTheme {
        LoginScreen()
    }
}