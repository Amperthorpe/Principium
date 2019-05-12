package com.alleluid.principium.common.items.basic

import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.common.items.BaseItem

abstract class ItemSubstruct(suffix: String) : BaseItem("substruct_$suffix")

object ItemSubstructBasic : ItemSubstruct("basic")
object ItemSubstructIron : ItemSubstruct("iron")
object ItemSubstructGold : ItemSubstruct("golden")
object ItemSubstructDiamond : ItemSubstruct("diamond")

object Substructs {
    @JvmStatic
    val variants = listOf(
            ItemSubstructBasic,
            ItemSubstructIron,
            ItemSubstructGold,
            ItemSubstructDiamond
    )
}