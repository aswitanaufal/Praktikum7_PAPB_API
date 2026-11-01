package com.example.praktikum4_navcompose.data.repository

import com.example.praktikum4_navcompose.data.model.Student
import com.example.praktikum4_navcompose.data.remote.StudentRemoteDataSource
import com.example.praktikum4_navcompose.data.remote.dto.toStudent

interface StudentRepository {
    fun getStudents(callback: (Result<List<Student>>) -> Unit)
    fun searchStudents(query: String, callback: (Result<List<Student>>) -> Unit)
    fun getStudentById(id: Int, callback: (Result<Student>) -> Unit)
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource
) : StudentRepository {
    override fun getStudents(
        callback: (Result<List<Student>>) -> Unit
    ) {
        remoteDataSource.getStudents { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }

    override fun getStudentById(
        id: Int,
        callback: (Result<Student>) -> Unit
    ) {
        remoteDataSource.getStudent(id) { result ->
            callback(result.map { it.toStudent() })
        }
    }

    override fun searchStudents(query: String, callback: (Result<List<Student>>) -> Unit) {
        remoteDataSource.searchStudents(query) { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }
}

