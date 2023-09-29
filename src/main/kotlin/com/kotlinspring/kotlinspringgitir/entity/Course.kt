package com.kotlinspring.kotlinspringgitir.entity

import jakarta.persistence.*

@Entity
@Table(name = "Courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Int?,
    var name : String,
    var category: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INSTRUCTOR_ID", nullable = false)
    val instructor: Instructor? = null
)
