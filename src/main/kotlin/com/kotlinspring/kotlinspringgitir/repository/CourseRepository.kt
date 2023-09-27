package com.kotlinspring.kotlinspringgitir.repository

import com.kotlinspring.kotlinspringgitir.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {
}