//package com.kotlinspring.controller
//
//import com.kotlinspring.kotlinspringgitir.dto.CourseDTO
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.AutoConfiguration
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.web.reactive.server.WebTestClient
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@AutoConfigureWebTestClient
//class CourseControllerIntgTest {
//
//    @Autowired
//    lateinit var webTestClient: WebTestClient
//
//    @Test
//    fun retrieveGreeting() {
//
//        val courseDTO = CourseDTO(null,"Build Restful APIs","Dilip")
//
//
//
//        val savedCourseDTO = webTestClient.post()
//            .uri("/v1/courses")
//            .bodyValue(courseDTO)
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody(CourseDTO::class.java)
//            .returnResult()
//            .responseBody
//
//        Assertions.assertTrue{
//            savedCourseDTO!!.id != null
//        }
//    }
//}


package com.kotlinspring.kotlinspringgitir.controller

import com.kotlinspring.kotlinspringgitir.dto.CourseDTO
import com.kotlinspring.kotlinspringgitir.entity.Course
import com.kotlinspring.kotlinspringgitir.repository.CourseRepository
import com.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUP(){
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun retrieveGreeting() {

        val courseDTO = CourseDTO(null, "Build Restful APIs", "Dilip")


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
    fun retrieveAllCourses(){

        val courseDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody


        println("CourseDTOs : $courseDTOs")
        Assertions.assertEquals(3,courseDTOs!!.size)

    }

    @Test
    fun updateCourse(){

        //existing Course

        val course = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)

        //courseId

        val updateCourseDTO = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin1", "Development")



        //Update CourseDTO

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}",course.id)
            .bodyValue(updateCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Build RestFul APis using SpringBoot and Kotlin1", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse(){

        //existing Course

        val course = Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)

        val updatedCourse = webTestClient.delete()
            .uri("/v1/courses/{courseId}",course.id)
            .exchange()
            .expectStatus().isNoContent

    }
}