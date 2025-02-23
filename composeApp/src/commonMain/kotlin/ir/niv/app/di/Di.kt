package ir.niv.app.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(koinModules)
}

val koinModules = listOf(
    viewModels,
    domainModules,
    dataModules,
    networkModules,
    apiModules
)