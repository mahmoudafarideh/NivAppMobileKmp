package ir.niv.app.domain.login

import ir.niv.app.data.login.LoginApi
import ir.niv.app.data.login.NumberRegisterDto

class LoginRepository(
    private val loginApi: LoginApi
) {
    suspend fun checkNumberRegistration(phoneNumber: String): PhoneNumberRegistrationState {
        return loginApi.registerNumber(phoneNumber).state.let {
            when (it) {
                NumberRegisterDto.StateDto.Login -> PhoneNumberRegistrationState.Registered
                NumberRegisterDto.StateDto.VerifyPhone -> PhoneNumberRegistrationState.Unregistered
            }
        }
    }
}