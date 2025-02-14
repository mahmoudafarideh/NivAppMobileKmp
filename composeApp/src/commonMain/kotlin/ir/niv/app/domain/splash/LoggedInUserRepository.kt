package ir.niv.app.domain.splash

import ir.niv.app.domain.core.settings

class LoggedInUserRepository {
    val id: Long = settings.getLong("User_Id", -1)
}