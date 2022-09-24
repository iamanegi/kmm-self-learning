package com.amannegi.kmmdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform