package com.example.praktikum4_navcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikum4_navcompose.data.remote.RetrofitClient
import com.example.praktikum4_navcompose.data.remote.StudentRemoteDataSource
import com.example.praktikum4_navcompose.data.repository.DefaultStudentRepository
import com.example.praktikum4_navcompose.ui.detail.DetailViewModel
import com.example.praktikum4_navcompose.ui.home.HomeViewModel
import com.example.praktikum4_navcompose.ui.screens.AboutScreen
import com.example.praktikum4_navcompose.ui.screens.DetailScreen
import com.example.praktikum4_navcompose.ui.screens.HomeScreen
import com.example.praktikum4_navcompose.ui.screens.ProfileScreen


// navcontroller: simpan sedang berada di route mana
// navhost: memandu agar ke compose yg tepat

@Composable
fun NavigationLabApp(){
    val navController = rememberNavController()
    val remoteDataSource = remember {
        StudentRemoteDataSource(RetrofitClient.apiService)
    }
    val repository = remember {
        DefaultStudentRepository(remoteDataSource)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ){
        composable(Routes.HOME){
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.factory(repository)
            )
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = homeUiState,
                onSearchQueryChange = homeViewModel::onSearchQueryChange,
                onStudentClick = { id -> navController.navigate(Routes.detail(id)) },
                onRetry = homeViewModel::loadStudents,
                onSearch = homeViewModel::refresh,
                onRefresh = homeViewModel::refresh
            )
        }
        composable(Routes.PROFILE){
            ProfileScreen(
                onBack = {navController.popBackStack()}
            )
        }
        composable(Routes.DETAIL, arguments = listOf(
            navArgument("studentId"){
                type = NavType.IntType
            }
        )){ idSebelum ->
            val studentId = idSebelum.arguments?.getInt("studentId")?: 0

            val viewModel: DetailViewModel =
                viewModel(factory = DetailViewModel.factory(repository))

            LaunchedEffect(key1 = studentId) {
                viewModel.loadStudent(studentId)
            }

            DetailScreen(
                onBack = {navController.popBackStack()},
                uiState = viewModel.uiState
            )
        }
        composable(Routes.ABOUT){
            AboutScreen(
                onBack = {navController.popBackStack()}
            )
        }
    }
}


