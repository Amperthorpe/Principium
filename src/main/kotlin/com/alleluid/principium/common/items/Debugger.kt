package com.alleluid.principium.common.items

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import com.alleluid.principium.Utils.mcFormat
import com.alleluid.principium.Utils.Formatting as mcf

object Debugger : BaseItem("debugger"){
    init {

    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("Some lore text".mcFormat(mcf.LORE))
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

    override fun getUnlocalizedName(): String {
        val name = super.getUnlocalizedName()
        return "[$name]"
    }
}