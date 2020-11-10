package masterchefdevs.colectiv.ubb.chefs.auth.data

import masterchefdevs.colectiv.ubb.chefs.auth.data.remote.RemoteAuthDataSource
import masterchefdevs.colectiv.ubb.chefs.core.Api
import  masterchefdevs.colectiv.ubb.chefs.core.Result

object LoginRepository {
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<TokenHolder> {
        val user = LoggedInUser(username, password)
        val result = RemoteAuthDataSource.login(user)
        if (result is Result.Success<TokenHolder>) {
            setLoggedInUser(user, result.data)
        }
        return result
    }

    private fun setLoggedInUser(user: LoggedInUser, tokenHolder: TokenHolder) {
        LoginRepository.user = user
        Api.tokenInterceptor.token = tokenHolder.token
    }
}
