package com.kotlinspring.kotlinspringgitir.service

import com.kotlinspring.kotlinspringgitir.dto.CourseDTO
import com.kotlinspring.kotlinspringgitir.entity.Course
import com.kotlinspring.kotlinspringgitir.exception.CourseNotFoundException
import com.kotlinspring.kotlinspringgitir.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val courseEntity = courseDTO.let {

            Course(null, it.name, it.category)

        }

        courseRepository.save(courseEntity)

        logger.info("Saved courses is : $courseEntity")

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }

    }

    fun retrieveAllCourses(): List<CourseDTO> {

        return courseRepository.findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }

    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): Any {

        val exsitingCourse = courseRepository.findById(courseId)

        return if (exsitingCourse.isPresent) {

            exsitingCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    CourseDTO(it.id,it.name,it.category)
                }

        } else {
            throw CourseNotFoundException("no course found for the passed Id : $courseId")
        }
    }

}
