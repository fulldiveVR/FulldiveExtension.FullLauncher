

package ch.deletescape.lawnchair.util

import android.view.View
import android.view.ViewParent

class ViewParents(private val view: View) : Iterable<ViewParent> {

    override fun iterator(): Iterator<ViewParent> = ViewParentIterator(view.parent)

    class ViewParentIterator(private var parent: ViewParent?) : Iterator<ViewParent> {

        override fun hasNext() = parent != null

        override fun next(): ViewParent {
            val current = parent ?: throw IllegalStateException("no next parent")
            parent = current.parent
            return current
        }
    }
}

val View.parents get() = ViewParents(this)
