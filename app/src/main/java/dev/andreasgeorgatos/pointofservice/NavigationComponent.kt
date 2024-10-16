package dev.andreasgeorgatos.pointofservice

import VerifyEmailScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.andreasgeorgatos.pointofservice.screens.credentials.LoginScreen
import dev.andreasgeorgatos.pointofservice.screens.credentials.registerform.RegisterScreen
import dev.andreasgeorgatos.pointofservice.screens.credentials.resetpassword.ForgotPasswordScreen
import dev.andreasgeorgatos.pointofservice.screens.credentials.resetpassword.ResetPasswordScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.AdminMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.CookHelperMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.CookMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.CustomerMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.ManagerMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.ServerMainScreen
import dev.andreasgeorgatos.pointofservice.screens.mainscreen.SystemMainScreen
import dev.andreasgeorgatos.pointofservice.screens.management.employees.ShowAllEmployeesScreen
import dev.andreasgeorgatos.pointofservice.screens.management.shift.EndShiftScreen
import dev.andreasgeorgatos.pointofservice.screens.management.shift.StartShiftScreen
import dev.andreasgeorgatos.pointofservice.screens.management.tables.add.AddMultipleTablesScreen
import dev.andreasgeorgatos.pointofservice.screens.management.tables.add.AddSingleTableScreen
import dev.andreasgeorgatos.pointofservice.screens.management.tables.add.AddTablesScreen
import dev.andreasgeorgatos.pointofservice.screens.management.tables.add.RemoveMultipleTables
import dev.andreasgeorgatos.pointofservice.screens.management.tables.remove.RemoveSingleTableScreen

import dev.andreasgeorgatos.pointofservice.screens.management.tables.remove.RemoveTablesScreen

const val LOGIN_ROUTE = "login_screen"
const val REGISTER_ROUTE = "register_screen"
const val FORGOT_PASSWORD_ROUTE = "forgot_password_screen"
const val VERIFY_EMAIL_ROUTE = "verify_email_screen"
const val RESET_PASSWORD_ROUTE = "reset_password_screen"
const val CUSTOMER_MAIN_SCREEN = "customer_main_screen"
const val SERVER_MAIN_SCREEN = "server_main_screen"
const val MANAGER_MAIN_SCREEN = "manager_main_screen"
const val COOK_HELPER_MAIN_SCREEN = "cook_helper_main_screen"
const val COOK_MAIN_SCREEN = "cook_main_screen"
const val ADMIN_MAIN_SCREEN = "admin_main_screen"
const val SYSTEM_MAIN_SCREEN = "system_main_screen"
const val ADD_TABLES_SCREEN = "add_tables_screen"
const val REMOVE_TABLES_SCREEN = "remove_tables_screen"
const val START_SHIFT_SCREEN = "start_shift_screen"
const val END_SHIFT_SCREEN = "end_shift_screen"
const val ADD_SINGLE_TABLE_SCREEN = "add_single_table_screen"
const val REMOVE_SINGLE_TABLE_SCREEN = "remove_single_table_screen"
const val ADD_MULTIPLE_TABLE_SCREEN = "add_multiple_table_screen"
const val REMOVE_MULTIPLE_TABLE_SCREEN = "remove_multiple_table_screen"
const val SHOW_ALL_EMPLOYEES_SCREEN = "show_all_employees_screen"

@SuppressLint("NewApi")
@Composable
fun NavigationComponent() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        composable(LOGIN_ROUTE) {
            LoginScreen(navController)
        }
        composable(REGISTER_ROUTE) {
            RegisterScreen(navController)
        }
        composable(FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(navController)
        }
        composable(RESET_PASSWORD_ROUTE) {
            ResetPasswordScreen(navController)
        }
        composable(SYSTEM_MAIN_SCREEN) {
            SystemMainScreen(navController)
        }
        composable(SERVER_MAIN_SCREEN) {
            ServerMainScreen(navController)
        }
        composable(MANAGER_MAIN_SCREEN) {
            ManagerMainScreen(navController)
        }
        composable(COOK_HELPER_MAIN_SCREEN) {
            CookHelperMainScreen(navController)
        }
        composable(COOK_MAIN_SCREEN) {
            CookMainScreen(navController)
        }
        composable(ADMIN_MAIN_SCREEN) {
            AdminMainScreen(navController)
        }
        composable(CUSTOMER_MAIN_SCREEN) {
            CustomerMainScreen(navController)
        }
        composable(START_SHIFT_SCREEN) {
            StartShiftScreen(navController)
        }
        composable(ADD_TABLES_SCREEN) {
            AddTablesScreen(navController)
        }
        composable(REMOVE_TABLES_SCREEN) {
            RemoveTablesScreen(navController)
        }
        composable(END_SHIFT_SCREEN) {
            EndShiftScreen(navController)
        }
        composable(ADD_SINGLE_TABLE_SCREEN) {
            AddSingleTableScreen(navController)
        }
        composable(REMOVE_SINGLE_TABLE_SCREEN) {
            RemoveSingleTableScreen(navController)
        }
        composable(ADD_MULTIPLE_TABLE_SCREEN) {
            AddMultipleTablesScreen(navController)
        }
        composable(REMOVE_MULTIPLE_TABLE_SCREEN) {
            RemoveMultipleTables(navController)
        }
        composable(SHOW_ALL_EMPLOYEES_SCREEN) {
            ShowAllEmployeesScreen(navController)
        }

        composable(
            route = "$VERIFY_EMAIL_ROUTE/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyEmailScreen(email, navController)
        }
    }
}
