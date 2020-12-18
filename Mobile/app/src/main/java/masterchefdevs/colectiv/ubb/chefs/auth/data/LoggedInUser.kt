package masterchefdevs.colectiv.ubb.chefs.auth.data

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val username: String,
        val password: String
)