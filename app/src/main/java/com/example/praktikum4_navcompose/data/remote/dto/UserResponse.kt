package com.example.praktikum4_navcompose.data.remote.dto

data class UserResponse(
    val users: List<UserDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)