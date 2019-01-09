package com.alleluid.principium.common.blocks

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils.Formatting
import com.alleluid.principium.Utils.mcFormat
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary
import java.util.*

open class BaseBlock(material: Material, val name: String): Block(material){
    init {
        translationKey= "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    internal val loreText: MutableList<String> = mutableListOf()
    internal val infoText: MutableList<String> = mutableListOf()
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        this.loreText.forEach { tooltip.add(it.mcFormat(Formatting.LORE)) }
        this.infoText.forEach { tooltip.add(it) }
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return Item.getItemFromBlock(this)
    }

    fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, name)
    }

    fun createItemBlock(): Item {
        return ItemBlock(this).setRegistryName(registryName)
    }

    fun initOreDict(ore: String): BaseBlock {
        OreDictionary.registerOre(ore,this)
        return this
    }


}