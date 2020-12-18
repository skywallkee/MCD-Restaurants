package masterchefdevs.colectiv.ubb.chefs.auth.data

data class NewUser (
    val username: String,
    val password1: String,
    val password2: String,
    val email: String
)