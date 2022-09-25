package com.amannegi.kmmdemo

class AndroidUser : User {
    override val email: String = "android@test.com"
    override val password: String = "android"
}

actual fun getUser(): User = AndroidUser()