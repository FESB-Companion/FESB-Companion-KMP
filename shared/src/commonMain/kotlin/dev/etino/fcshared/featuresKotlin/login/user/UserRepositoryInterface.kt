package dev.etino.fcshared.featuresKotlin.login.user

import dev.etino.fcshared.featuresKotlin.login.user.models.User
import dev.etino.fcshared.featuresKotlin.login.user.models.UserRepositoryResult
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    val showGithubMessage: Flow<Boolean>

    suspend fun attemptLogin(username: String, password: String): UserRepositoryResult.LoginResult

    suspend fun insertDummyUser()

    suspend fun getCurrentUserName(): String

    suspend fun getCurrentUser(): User

    suspend fun deleteAllUserData()

    suspend fun hideGithubMessage()

}
