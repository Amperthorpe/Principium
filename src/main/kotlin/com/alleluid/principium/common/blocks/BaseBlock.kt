package com.alleluid.principium.common.blocks

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.util.Formatting
import com.alleluid.principium.util.LangType
import com.alleluid.principium.util.langKey
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
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
        this.loreText.forEach { tooltip.add(Formatting.LORE + it + Formatting.RESET) }
        this.infoText.forEach { tooltip.add(it) }
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

        // Adds string as key already fully qualified as type.mod_id.name.
    fun MutableList<String>.addNamedKey(key: String, vararg args: Any){
        this.add(I18n.format(langKey(LangType.INFO, "$name.$key"), args))
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