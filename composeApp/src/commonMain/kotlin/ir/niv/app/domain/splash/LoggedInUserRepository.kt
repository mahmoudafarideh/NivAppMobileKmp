package ir.niv.app.domain.splash

import ir.niv.app.domain.repository.LocalStorage
import ir.niv.app.domain.repository.data

class LoggedInUserRepository(
    localStorage: LocalStorage
) {
    val id: Long by localStorage.data("User_Id", -1)
}