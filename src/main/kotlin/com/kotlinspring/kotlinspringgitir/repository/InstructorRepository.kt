package com.kotlinspring.kotlinspringgitir.repository

import com.kotlinspring.kotlinspringgitir.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor,Int> {
}