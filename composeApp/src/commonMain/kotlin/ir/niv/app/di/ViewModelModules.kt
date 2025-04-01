package ir.niv.app.di

import ir.niv.app.ui.AppViewModel
import ir.niv.app.ui.home.screen.HomeViewModel
import ir.niv.app.ui.login.screen.LoginViewModel
import ir.niv.app.ui.splash.screen.SplashViewModel
import ir.niv.app.ui.support.details.TicketDetailsViewModel
import ir.niv.app.ui.support.list.TicketsViewModel
import ir.niv.app.ui.support.submit.SubmitTicketViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModels = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::TicketsViewModel)
    viewModelOf(::SubmitTicketViewModel)
    viewModelOf(::TicketDetailsViewModel)
}