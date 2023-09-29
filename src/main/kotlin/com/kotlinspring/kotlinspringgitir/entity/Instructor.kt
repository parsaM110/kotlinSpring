package com.kotlinspring.kotlinspringgitir.entity

import jakarta.persistence.*

@Entity
@Table(name = "INSTRUCTORS")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Int?,
    var name : String,
)
