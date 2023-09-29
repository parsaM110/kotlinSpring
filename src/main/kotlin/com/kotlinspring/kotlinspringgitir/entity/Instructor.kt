package com.kotlinspring.kotlinspringgitir.entity

import jakarta.persistence.*

@Entity
@Table(name = "INSTRUCTORS")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    var name: String,

    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    ) // Define the one-to-many relationship
    var courses: List<Course> = mutableListOf()
)

