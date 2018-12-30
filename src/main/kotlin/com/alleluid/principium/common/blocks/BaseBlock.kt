package com.alleluid.principium.common.blocks

import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.MOD_ID
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.OreDictionary
import java.util.*

open class BaseBlock(material: Material, val name: String): Block(material){
    init {
        unlocalizedName= "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
    }

    override fun getCreativeTabToDisplayOn(): CreativeTabs {
        return PrincipiumMod.creativeTab
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