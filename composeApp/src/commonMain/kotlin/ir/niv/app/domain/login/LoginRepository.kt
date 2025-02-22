package ir.niv.app.domain.login

import ir.niv.app.api.login.LoginApi
import ir.niv.app.api.login.NumberRegisterDto
import ir.niv.app.domain.core.PhoneNumber
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.domain.repository.AuthRepository

class LoginRepository(
    private val loginApi: LoginApi,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend fun checkNumberRegistration(phoneNumber: String): PhoneNumberRegistrationState {
        return loginApi.registerNumber(phoneNumber).state.let {
            when (it) {
                NumberRegisterDto.StateDto.Login -> PhoneNumberRegistrationState.Registered
                NumberRegisterDto.StateDto.VerifyPhone -> PhoneNumberRegistrationState.Unregistered
            }
        }
    }

    suspend fun loginUsingOtp(
        phoneNumber: PhoneNumber,
        otp: Otp
    ) {
        return loginApi.login(phoneNumber, otp).let {
            authRepository.updateTokens(it.accessToken, it.refreshToken)
            userRepository.updateUser(it.user)
        }
    }
}