
package ch.deletescape.lawnchair.util

import java.lang.reflect.Field

inline fun <reified T> getField(name: String): Field {
    return T::class.java.getDeclaredField(name).apply {
        isAccessible = true
    }
}
