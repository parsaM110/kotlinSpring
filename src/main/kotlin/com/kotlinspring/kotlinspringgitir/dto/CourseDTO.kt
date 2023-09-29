package com.kotlinspring.kotlinspringgitir.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO (
    val id : Int?,
    @get:NotBlank(message = "CourseDTO.name must not be blank")
    val name : String,
    @get:NotBlank(message = "CourseDTO.category must not be blank")
    val category: String,
)