

package ch.deletescape.lawnchair.util.diff

import android.os.Bundle

class BundleDiff(first: Bundle, second: Bundle) : Diff<String, Bundle>(first, second) {
    override val added: List<String> by lazy { second.keySet().filterNot { first.containsKey(it) } }
    override val removed: List<String> by lazy { first.keySet().filterNot { second.containsKey(it) } }
    override val changed: List<String> by lazy { first.keySet().filter { second.containsKey(it) }.filter { first[it] != second[it] } }
}

inline infix fun Bundle.diff(other: Bundle): BundleDiff = BundleDiff(this, other)