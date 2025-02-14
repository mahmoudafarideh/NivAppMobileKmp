package ir.niv.app.di

import ir.niv.app.domain.splash.LoggedInUserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModules = module {
    singleOf(::LoggedInUserRepository)
}