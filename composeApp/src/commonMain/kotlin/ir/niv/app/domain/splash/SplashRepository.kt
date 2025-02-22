package ir.niv.app.domain.splash

import ir.niv.app.api.login.LoginApi
import ir.niv.app.domain.core.UserRepository

class SplashRepository(
    private val loginApi: LoginApi,
    private val userRepository: UserRepository
) {
    suspend fun checkLogin() {
        userRepository.updateUser(loginApi.checkLogin().user)
    }
}