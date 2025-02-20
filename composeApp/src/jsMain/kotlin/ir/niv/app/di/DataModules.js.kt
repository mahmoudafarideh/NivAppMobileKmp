package ir.niv.app.di

import com.russhwolf.settings.StorageSettings
import ir.niv.app.domain.repository.LocalStorage
import ir.niv.app.domain.repository.LocalStorageImp
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

actual fun Module.storageModule(): KoinDefinition<LocalStorage> {
    return single {
        LocalStorageImp(StorageSettings())
    }
}