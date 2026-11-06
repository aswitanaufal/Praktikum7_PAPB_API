package com.example.praktikum4_navcompose.data.repository

import com.example.praktikum4_navcompose.data.model.Student
import com.example.praktikum4_navcompose.data.remote.StudentRemoteDataSource
import com.example.praktikum4_navcompose.data.remote.dto.toStudent

interface StudentRepository {
    suspend fun getStudents():List<Student>
    suspend fun searchStudents(query: String): List<Student>
    suspend fun getStudentById(id: Int): Student
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource
) : StudentRepository {

    override suspend fun getStudents(): List<Student> {
        return remoteDataSource.getStudents().map{it.toStudent()}
    }

    override suspend fun searchStudents(query: String): List<Student> {
        return remoteDataSource.searchStudents(query=query).map{it.toStudent()}
    }

    override suspend fun getStudentById(id: Int): Student {
        return remoteDataSource.getStudent(id=id).toStudent()
    }
}

