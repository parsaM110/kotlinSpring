package com.kotlinspring.kotlinspringgitir.controller

import com.kotlinspring.kotlinspringgitir.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient


@AutoConfigureWebTestClient
@WebMvcTest(controllers = [GreetingController::class])
class GreetingControllerUnitTest{

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveGreeting() {

        val name = "Dilip"

        every { greetingServiceMock.retrieveGreeting(any()) } returns  "Hello $name "

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}",name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("Hello $name ", result.responseBody)
    }
}