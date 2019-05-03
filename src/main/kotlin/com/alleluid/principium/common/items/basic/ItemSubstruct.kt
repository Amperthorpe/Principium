package com.alleluid.principium.common.items.basic

import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.common.items.BaseItem

object ItemSubstruct : BaseItem("substruct") {
    val variants: List<String> = listOf(
            "basic",
            "iron",
            "golden",
            "diamond"
    )

    override fun registerItemModel() {
        var idx = 0
        variants.forEach {
            PrincipiumMod.proxy.registerItemRenderer(this, idx++, "${name}_$it")
        }
    }
}