package ir.niv.app.di

import ir.niv.app.domain.login.LoginRepository
import ir.niv.app.domain.repository.AuthRepository
import ir.niv.app.domain.splash.LoggedInUserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModules = module {
    singleOf(::LoggedInUserRepository)
    singleOf(::LoginRepository)
    singleOf(::AuthRepository)
}