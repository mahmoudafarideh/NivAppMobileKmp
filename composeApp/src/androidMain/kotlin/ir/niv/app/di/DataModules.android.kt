package ir.niv.app.di

import com.russhwolf.settings.SharedPreferencesSettings
import ir.niv.app.domain.repository.LocalStorage
import ir.niv.app.domain.repository.LocalStorageImp
import org.koin.android.ext.koin.androidContext
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

actual fun Module.storageModule(): KoinDefinition<LocalStorage> = single {
    LocalStorageImp(
        SharedPreferencesSettings
            .Factory(androidContext())
            .create(null)
    )
}