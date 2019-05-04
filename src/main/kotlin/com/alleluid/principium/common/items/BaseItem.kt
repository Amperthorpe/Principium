package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.GeneralUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary

open class BaseItem(val name: String): Item(){
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    internal val loreText: MutableList<String> = mutableListOf()
    internal val infoText: MutableList<String> = mutableListOf()
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        this.loreText.forEach { tooltip.add(GeneralUtils.Formatting.LORE + it + GeneralUtils.Formatting.RESET) }
        this.infoText.forEach { tooltip.add(it) }
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }


    open fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

    fun initOreDict(ore: String): BaseItem {
        OreDictionary.registerOre(ore,this)
        return this
    }


}