package dev.andreasgeorgatos.pointofservice

import VerifyEmailScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.andreasgeorgatos.pointofservice.forms.credentials.LoginScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.registerform.RegisterScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.resetpassword.ForgotPasswordScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.resetpassword.ResetPasswordScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.AdminMainScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.CookHelperMainScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.CookMainScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.CustomerMainScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.ManagerMainScreen
import dev.andreasgeorgatos.pointofservice.forms.mainscreen.ServerMainScreen

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
        composable(CUSTOMER_MAIN_SCREEN) {
            CustomerMainScreen(navController)
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
        composable(
            route = "$VERIFY_EMAIL_ROUTE/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyEmailScreen(email, navController)
        }
    }
}
