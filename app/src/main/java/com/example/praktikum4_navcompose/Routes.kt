package com.example.praktikum4_navcompose

object Routes {
    // const = objectnya ketika compile
    const val HOME ="home"
    const val PROFILE = "profile"
    const val DETAIL ="detail/{studentId}"
    const val ABOUT ="about"

    fun detail(studentId: Int): String = "detail/$studentId"
}