import dev.andreasgeorgatos.pointofservice.data.dto.CredentialsDTO
import dev.andreasgeorgatos.pointofservice.data.dto.EmailDTO
import dev.andreasgeorgatos.pointofservice.data.dto.ResetPasswordDTO
import dev.andreasgeorgatos.pointofservice.data.dto.UserDTO
import dev.andreasgeorgatos.pointofservice.data.dto.VerificationDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET("users")
    fun getUsers(): Call<List<UserDTO>>

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
}
