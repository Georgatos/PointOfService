import dev.andreasgeorgatos.pointofservice.data.dto.CredentialsDTO
import dev.andreasgeorgatos.pointofservice.data.dto.EmailDTO
import dev.andreasgeorgatos.pointofservice.data.dto.PermissionDTO
import dev.andreasgeorgatos.pointofservice.data.dto.ResetPasswordDTO
import dev.andreasgeorgatos.pointofservice.data.dto.UserDTO
import dev.andreasgeorgatos.pointofservice.data.dto.UserNameDTO
import dev.andreasgeorgatos.pointofservice.data.dto.VerificationDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.security.Permission

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
}
