package ir.niv.app.di

import ir.niv.app.domain.repository.LocalStorage
import kotlinx.serialization.json.Json
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun Module.storageModule(): KoinDefinition<LocalStorage>

val dataModules = module {
    storageModule()
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}