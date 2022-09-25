package com.amannegi.kmmdemo

interface User {
    val email: String
    val password: String
}

expect fun getUser(): User