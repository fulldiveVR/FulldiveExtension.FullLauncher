

package ch.deletescape.lawnchair.preferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import ch.deletescape.lawnchair.LawnchairAppFilter
import com.android.launcher3.R
import com.android.launcher3.Utilities

class HiddenAppsFragment : RecyclerViewFragment(), SelectableAppsAdapter.Callback {

    private lateinit var adapter: SelectableAppsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onRecyclerViewCreated(recyclerView: RecyclerView) {
        val context = recyclerView.context
        adapter = SelectableAppsAdapter.ofProperty(context,
                Utilities.getLawnchairPrefs(context)::hiddenAppSet, this, LawnchairAppFilter(context))
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
                LinearLayoutManager(context,
                                                                                    LinearLayoutManager.VERTICAL,
                                                                                    false)
        recyclerView.adapter = adapter
    }

    override fun onSelectionsChanged(newSize: Int) {
        if (newSize > 0) {
            activity?.title = "$newSize${getString(R.string.hide_app_selected)}"
        } else {
            activity?.title = getString(R.string.hide_apps)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_hide_apps, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                adapter.clearSelection()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
