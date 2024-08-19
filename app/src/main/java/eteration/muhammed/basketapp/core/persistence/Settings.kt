package eteration.muhammed.basketapp.core.persistence

import android.content.Context
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

class Setting<T>(val key: String, val default: T)


fun Setting<Int>.readInt(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)

fun Setting<Long>.readLong(context: Context) =
    EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
        .persistent()
        .read(key, default)


fun Setting<Int>.saveInt(context: Context, value: Int) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}

fun Setting<Long>.saveLong(context: Context, value: Long) {
    val entryPoint = EntryPointAccessors.fromApplication(context, PersistenceEntryPoint::class.java)
    entryPoint.coroutine().launch {
        entryPoint.persistent().save(key, value)
    }
}