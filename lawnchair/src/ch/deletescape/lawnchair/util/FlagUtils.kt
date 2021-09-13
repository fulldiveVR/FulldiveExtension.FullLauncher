

package ch.deletescape.lawnchair.util

fun Int.hasFlag(flag: Int): Boolean {
    return (this and flag) != 0
}

fun Int.hasFlags(vararg flags: Int): Boolean {
    return flags.all { hasFlag(it) }
}

fun Int.addFlag(flag: Int): Int {
    return this or flag
}

fun Int.removeFlag(flag: Int): Int {
    return this and flag.inv()
}

fun Int.toggleFlag(flag: Int): Int {
    return if (hasFlag(flag)) removeFlag(flag) else addFlag(flag)
}

fun Int.setFlag(flag: Int, value: Boolean): Int {
    return if (value) {
        addFlag(flag)
    } else {
        removeFlag(flag)
    }
}
