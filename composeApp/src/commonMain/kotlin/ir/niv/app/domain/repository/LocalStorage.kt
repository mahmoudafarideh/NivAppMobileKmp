@file:Suppress("UNCHECKED_CAST")

package ir.niv.app.domain.repository

import com.russhwolf.settings.Settings
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializerOrNull
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface LocalStorage {
    fun <T : Any> getData(key: String, clazz: KClass<T>, defaultValue: T?): T?
    fun <T : Any> setData(key: String, clazz: KClass<T>, value: T?)
}

class LocalStorageImp(
    private val settings: Settings,
    private val json: Json
) : LocalStorage {

    override fun <T : Any> getData(
        key: String,
        clazz: KClass<T>,
        defaultValue: T?
    ): T? {
        if (!settings.hasKey(key)) return defaultValue
        return when (clazz) {
            Int::class ->
                settings.getInt(key, (defaultValue ?: 0) as Int) as T

            Long::class ->
                settings.getLong(key, (defaultValue ?: 0) as Long) as T

            String::class ->
                settings.getString(key, (defaultValue as? String).orEmpty()) as T?

            Boolean::class ->
                settings.getBoolean(key, (defaultValue ?: false) as Boolean) as T

            else -> {
                getSerializer(clazz)?.let {
                    val string = settings.getString(key, "")
                    return json.decodeFromString(it, string)
                }
                throw IllegalArgumentException()
            }
        }
    }

    @OptIn(InternalSerializationApi::class)
    private fun <T : Any> getSerializer(clazz: KClass<T>): KSerializer<T>? {
        return runCatching {
            clazz.serializerOrNull()
        }.getOrNull()
    }

    override fun <T : Any> setData(
        key: String,
        clazz: KClass<T>,
        value: T?
    ) {
        if (value == null) {
            settings.remove(key)
        } else {
            when (clazz) {
                Int::class ->
                    settings.putInt(key, value as Int)

                String::class ->
                    settings.putString(key, value as String)

                Long::class ->
                    settings.putLong(key, value as Long)

                Boolean::class ->
                    settings.putBoolean(key, value as Boolean)

                else -> {
                    getSerializer(clazz)?.let {
                        settings.putString(key, json.encodeToString(it, value))
                        return
                    }
                    throw IllegalArgumentException()
                }
            }
        }
    }

}

inline fun <reified T : Any> LocalStorage.data(
    key: String,
    default: T
): ReadWriteProperty<Any?, T> {

    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            getData(key, T::class, default)!!

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            setData(key, T::class, value)
        }
    }
}

inline fun <reified T : Any> LocalStorage.optional(
    key: String,
    default: T? = null
): ReadWriteProperty<Any?, T?> {

    return object : ReadWriteProperty<Any?, T?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
            getData(key, T::class, default)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            setData(key, T::class, value)
        }
    }
}