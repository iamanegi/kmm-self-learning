package com.amannegi.kmmdemo

class Auth {

    private val user: User = getUser()

    fun authenticateUser(email: String, password: String): AuthResponse {
        return when {
            (user.email != email) -> AuthResponse(false, "Incorrect email.")
            (user.password != password) -> AuthResponse(false, "Incorrect password.")
            else -> AuthResponse(true, "Login successful.")
        }
    }
}

data class AuthResponse(val isAuthenticated: Boolean, val message: String)