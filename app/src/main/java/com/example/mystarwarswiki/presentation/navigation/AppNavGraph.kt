package com.example.mystarwarswiki.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mystarwarswiki.presentation.detail.DetailScreen
import com.example.mystarwarswiki.presentation.people.PeopleScreen

object Routes {
    const val PEOPLE = "people"
    const val DETAIL = "detail/{personId}"

    fun detail(personId: Int) = "detail/$personId"
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PEOPLE,
        modifier = modifier
    ) {
        composable(Routes.PEOPLE) {
            PeopleScreen(
                onPersonClick = { personId ->
                    navController.navigate(Routes.detail(personId))
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("personId") { type = NavType.StringType }
            )
        ) {
            DetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}