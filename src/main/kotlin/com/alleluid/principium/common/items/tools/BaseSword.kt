package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import com.alleluid.principium.Utils.Formatting as ufm
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World


open class BaseSword(material: Item.ToolMaterial, val name: String) : ItemSword(material){
    init {
        unlocalizedName = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    fun registerItemModel(){
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }
}

object PrincipicSword : BaseItem("sword_principic"){

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("${ufm.LORE}The strong will be made weak, and the weak shall bow before me.")
        tooltip.add("Attacks lower to half a heart, but won't kill.")
//        tooltip.add("Right click to teleport, sneak click to set position.")
    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        return false //logic moved to CommonEvents.kt
   }

    override fun itemInteractionForEntity(stack: ItemStack, playerIn: EntityPlayer, target: EntityLivingBase, hand: EnumHand): Boolean {
        if (!playerIn.world.isRemote) Utils.statusMessage(target.health.toString())
        return super.itemInteractionForEntity(stack, playerIn, target, hand)
    }
}