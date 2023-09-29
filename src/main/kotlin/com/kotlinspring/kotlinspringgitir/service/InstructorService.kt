package com.kotlinspring.kotlinspringgitir.service

import com.kotlinspring.kotlinspringgitir.dto.InstructorDTO
import com.kotlinspring.kotlinspringgitir.entity.Instructor
import com.kotlinspring.kotlinspringgitir.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class InstructorService(val instructorRepository : InstructorRepository) {
    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructorEntity = instructorDTO.let{
            Instructor(it.id,it.name)
        }

        instructorRepository.save(instructorEntity)

        return instructorEntity.let{
            InstructorDTO(it.id,it.name)
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {

        return instructorRepository.findById(instructorId)
    }

}
