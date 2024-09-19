import dev.andreasgeorgatos.pointofservice.data.dto.user.CredentialsDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.EmailDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.EmployeeDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.PermissionDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.ResetPasswordDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.UserNameDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.VerificationDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("users/register")
    fun registerUser(@Body requestBody: RequestBody): Call<Void>

    @POST("users/login")
    fun loginUser(@Body credentials: CredentialsDTO): Call<Void>

    @POST("users/verify")
    fun verifyUser(@Body verificationDTO: VerificationDTO): Call<Void>

    @POST("users/forgotPassword")
    fun forgotPassword(@Body emailDTO: EmailDTO): Call<Void>

    @POST("users/resetPassword")
    fun resetPassword(@Body resetPasswordDTO: ResetPasswordDTO): Call<Void>

    @POST("users/getPermissions")
    fun getUserPermissions(@Body userNameDTO: UserNameDTO): Call<List<PermissionDTO>>

    @GET("users/getAllEmployees")
    fun getAllEmployees(): Call<Map<Long, EmployeeDTO>>
}
