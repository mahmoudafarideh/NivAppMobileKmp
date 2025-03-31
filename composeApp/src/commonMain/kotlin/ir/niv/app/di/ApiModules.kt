package ir.niv.app.di

import ir.niv.app.api.login.LoginApi
import ir.niv.app.api.support.TicketsApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val apiModules = module {
    singleOf(::LoginApi)
    singleOf(::TicketsApi)
}
