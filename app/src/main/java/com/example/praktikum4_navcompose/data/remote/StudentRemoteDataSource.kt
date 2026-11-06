package com.example.praktikum4_navcompose.data.remote

import com.example.praktikum4_navcompose.data.remote.dto.UserDto

class StudentRemoteDataSource(private val api: StudentApiService) {
    suspend fun getStudents(): List<UserDto> {
        return api.getUsers(limit = 10).users
    }

    suspend fun getStudent(id: Int): UserDto {
        return api.getUserById(id = id)
    }

    suspend fun searchStudents(query: String): List<UserDto> {
        return api.searchUsers(query = query).users
    }
}