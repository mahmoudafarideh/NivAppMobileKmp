package ir.niv.app.domain.repository

class AuthRepository(localStorage: LocalStorage) {
    val accessToken: String? by localStorage.optional("AccessToken")
    val refreshToken: String? by localStorage.optional("RefreshToken")
}