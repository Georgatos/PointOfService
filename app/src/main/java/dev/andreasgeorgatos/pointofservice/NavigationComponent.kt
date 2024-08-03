package dev.andreasgeorgatos.pointofservice

import VerifyEmailScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.andreasgeorgatos.pointofservice.forms.credentials.resetpassword.ForgotPasswordScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.LoginScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.registerform.RegisterScreen
import dev.andreasgeorgatos.pointofservice.forms.credentials.resetpassword.ResetPasswordScreen

const val LOGIN_ROUTE = "login"
const val REGISTER_ROUTE = "register"
const val FORGOT_PASSWORD_ROUTE = "forgot_password"
const val VERIFY_EMAIL_ROUTE = "verify_email"
const val RESET_PASSWORD = "reset_password"


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
        composable(RESET_PASSWORD) {
            ResetPasswordScreen(navController)
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
