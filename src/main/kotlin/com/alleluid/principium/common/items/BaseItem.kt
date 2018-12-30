package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.OreDictionary

open class BaseItem(val name: String): Item(){
    init {
        unlocalizedName = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    open fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

    fun initOreDict(ore: String): BaseItem {
        OreDictionary.registerOre(ore,this)
        return this
    }


}