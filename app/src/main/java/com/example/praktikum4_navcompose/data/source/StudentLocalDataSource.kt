//package com.example.praktikum4_navcompose.data.source
//
//import com.example.praktikum4_navcompose.data.model.Student
//
//class StudentLocalDataSource {
//    private val students = listOf(
//        Student(2026, "Aliya Putri", "Teknik Informatika", 2026),
//        Student(2027, "Bima Pratama", "Teknik Informatika", 2026),
//        Student(2028, "Citra Lestari", "Teknik Informatika", 2026)
//    )
//
//    fun getStudents(): List<Student> = students
//
//    fun getStudentById(id: Int): Student? = students.find { it.id == id }
//}