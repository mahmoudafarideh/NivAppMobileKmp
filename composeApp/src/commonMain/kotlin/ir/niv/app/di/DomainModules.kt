package ir.niv.app.di

import ir.niv.app.domain.core.UserRepository
import ir.niv.app.domain.login.LoginRepository
import ir.niv.app.domain.repository.AuthRepository
import ir.niv.app.domain.search.SearchRepository
import ir.niv.app.domain.splash.SplashRepository
import ir.niv.app.domain.support.SupportRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModules = module {
    singleOf(::LoginRepository)
    singleOf(::AuthRepository)
    singleOf(::UserRepository)
    singleOf(::SplashRepository)
    singleOf(::SupportRepository)
    singleOf(::SearchRepository)
}