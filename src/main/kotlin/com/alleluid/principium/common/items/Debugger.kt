package com.alleluid.principium.common.items

object Debugger : BaseItem("debugger"){
    init {
        loreText.add("Pay no attention to the item behind the curtain")
    }

    override fun getTranslationKey(): String {
        val name = super.getTranslationKey()
        return "[$name]"
    }
}