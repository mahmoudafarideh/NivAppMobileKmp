package ir.niv.app.di

import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = org.koin.core.context.startKoin {
    appDeclaration()
    koinModules
}

val koinModules = listOf(viewModels, domainModules)