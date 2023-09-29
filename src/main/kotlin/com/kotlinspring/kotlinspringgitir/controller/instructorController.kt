package com.kotlinspring.kotlinspringgitir.controller

import com.kotlinspring.kotlinspringgitir.dto.InstructorDTO
import com.kotlinspring.kotlinspringgitir.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/instructors")
@Validated
class instructorController(val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody instructorDTO: InstructorDTO) = instructorService.createInstructor(instructorDTO)

}