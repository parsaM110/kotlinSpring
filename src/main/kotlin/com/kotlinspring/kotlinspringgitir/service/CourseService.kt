package com.kotlinspring.kotlinspringgitir.service

import com.kotlinspring.kotlinspringgitir.dto.CourseDTO
import com.kotlinspring.kotlinspringgitir.entity.Course
import com.kotlinspring.kotlinspringgitir.exception.CourseNotFoundException
import com.kotlinspring.kotlinspringgitir.exception.InstructorNotValidException
import com.kotlinspring.kotlinspringgitir.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(
    val courseRepository: CourseRepository,
    val instructorService: InstructorService
) {

    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val instructorOptional = instructorService.findByInstructorId(courseDTO.instructorId!!)

        if (!instructorOptional.isPresent) {
            throw InstructorNotValidException("Instructor Not Valid for the Id : ${courseDTO.instructorId}")
        }

        val courseEntity = courseDTO.let {

            Course(null, it.name, it.category, instructorOptional.get())

        }

        courseRepository.save(courseEntity)

        logger.info("Saved courses is : $courseEntity")

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category, it.instructor!!.id)
        }

    }

    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {

        val courses = courseName?.let {
            courseRepository.findCourseByName(courseName)
        } ?: courseRepository.findAll()

//        return courseRepository.findAll()
//            .map {
//                CourseDTO(it.id, it.name, it.category)
//            }

        return courses
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
                    CourseDTO(it.id, it.name, it.category)
                }

        } else {
            throw CourseNotFoundException("no course found for the passed Id : $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {

        val exsitingCourse = courseRepository.findById(courseId)

        if (exsitingCourse.isPresent) {

            exsitingCourse.get()
                .let {
                    courseRepository.deleteById(courseId)

                }

        } else {
            throw CourseNotFoundException("no course found for the passed Id : $courseId")
        }

    }


}
