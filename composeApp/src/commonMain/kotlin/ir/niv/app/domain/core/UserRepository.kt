package ir.niv.app.domain.core

import ir.niv.app.api.core.UserDto
import ir.niv.app.api.core.toUser
import ir.niv.app.domain.repository.LocalStorage
import ir.niv.app.domain.repository.optional
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class UserRepository(localStorage: LocalStorage) {
    private var _user: UserDto? by localStorage.optional("user_info")
    private val _userFlow = MutableStateFlow(_user?.toUser())
    val userFlow = _userFlow.asStateFlow()

    fun updateUser(userDto: UserDto) {
        _userFlow.value = userDto.toUser()
        _user = userDto
    }

    fun clear() {
        _user = null
        _userFlow.value = null
    }
}