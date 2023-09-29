package com.kotlinspring.kotlinspringgitir.repository

import com.kotlinspring.kotlinspringgitir.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName : String) : List<Course>

    @Query(value = "SELECT * FROM COURSES where name like %?1%", nativeQuery = true)
    fun findCourseByName(courseName : String) : List<Course>
}