package com.kotlinspring.kotlinspringgitir.controller

import com.kotlinspring.kotlinspringgitir.dto.CourseDTO
import com.kotlinspring.kotlinspringgitir.entity.Course
import com.kotlinspring.kotlinspringgitir.service.CourseService
import com.kotlinspring.kotlinspringgitir.service.GreetingService
import com.kotlinspring.util.courseDTO
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
@WebMvcTest(controllers = [CourseController::class])
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService

    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(null, "Build Restful APIs", "Dilip")

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)


        val savedCourseDTO = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMockk.retrieveAllCourses() }.returnsMany(
            listOf(
                courseDTO(id = 1),
                courseDTO(id = 2, name = "Build Reactive Microservices using Spring WebFlux/SpringBoot")
            )
        )

        val courseDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody


        println("CourseDTOs : $courseDTOs")
        Assertions.assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse(){

        //existing Course

        val course = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")


        every { courseServiceMockk.updateCourse(any(), any())} returns  courseDTO(id = 100,"Build RestFul APis using SpringBoot and Kotlin1")


        val updateCourseDTO = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development")



        //Update CourseDTO

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}",100)
            .bodyValue(updateCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Build RestFul APis using SpringBoot and Kotlin1", updatedCourse!!.name)
    }

}