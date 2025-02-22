package ir.niv.app.domain.repository

class AuthRepository(localStorage: LocalStorage) {

    var accessToken: String? by localStorage.optional("AccessToken")
    var refreshToken: String? by localStorage.optional("RefreshToken")

    fun updateTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}