package com.kotlinspring.kotlinspringgitir.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingsService {

    @Value("\${message}")
    lateinit var message : String
    fun retrieveGreeting(name : String) = "Hello $name , $message"
}