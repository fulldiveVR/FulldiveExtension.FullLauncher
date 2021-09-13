
package ch.deletescape.lawnchair.flowerpot.rules

sealed class Rule {
    class None: Rule()
    data class Version(val version: Int): Rule()
    data class Package(val filter: String): Rule()
    data class IntentAction(val action: String): Rule()
    data class IntentCategory(val category: String): Rule()
    data class CodeRule(val rule: String, val args: Array<String>) : Rule()

    companion object {
        val NONE = None()
    }
}
