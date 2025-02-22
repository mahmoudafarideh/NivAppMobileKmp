package ir.niv.app.domain.repository

class AuthRepository(localStorage: LocalStorage) {
    private var _accessToken: String? by localStorage.optional("AccessToken")
    private var _refreshToken: String? by localStorage.optional("RefreshToken")
    val accessToken: String? = _accessToken
    val refreshToken: String? = _refreshToken

    fun updateTokens(accessToken: String, refreshToken: String) {
        _refreshToken = accessToken
        _refreshToken = refreshToken
    }
}