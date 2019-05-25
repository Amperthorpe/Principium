package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.util.Formatting
import com.alleluid.principium.util.LangType
import com.alleluid.principium.util.langKey
import net.minecraft.client.resources.I18n
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

    val loreText = mutableListOf<String>()
    val infoText = mutableListOf<String>()

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        this.loreText.forEach { tooltip.add(Formatting.LORE + it + Formatting.RESET) }
        this.infoText.forEach { tooltip.add(it) }
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }


    // Adds string as key already fully qualified as type.mod_id.name.
    fun MutableList<String>.addNamedKey(key: String, vararg args: Any){
        this.add(I18n.format(langKey(LangType.INFO, "$name.$key"), args))
    }

    open fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

    fun initOreDict(ore: String): BaseItem {
        OreDictionary.registerOre(ore,this)
        return this
    }


}