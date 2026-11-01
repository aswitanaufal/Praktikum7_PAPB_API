package com.example.praktikum4_navcompose.data.remote.dto

import com.example.praktikum4_navcompose.data.model.Student
import com.google.gson.annotations.SerializedName

// Data transfer Object
data class UserDto(
    val id: Int,
//    @SerializedName("firstName") val firstName: String?,
//    @SerializedName("lastName") val lastName: String?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val university: String
)

fun UserDto.toStudent() = Student(
    id = this.id,
    name = "${firstName.orEmpty()} ${lastName.orEmpty()}".trim(),
    email = this.email,
    university = this.university
)