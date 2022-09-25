package com.amannegi.kmmdemo

class IOSUser: User {
    override val email: String = "ios@test.com"
    override val password: String = "ios"
}

actual fun getUser(): User = IOSUser()