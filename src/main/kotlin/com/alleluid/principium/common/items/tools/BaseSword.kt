package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.MOD_NAME
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.nbt.NBTTagByte
import net.minecraft.nbt.NBTTagString
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation

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

object PrincipicSword : BaseSword(PrincipiumMod.principicToolMaterial, "sword_principic"){
    init {
    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        if (entity.isEntityAlive) {
            player.world.removeEntity(entity)
        }
        return super.onLeftClickEntity(stack, player, entity)
    }

    override fun itemInteractionForEntity(stack: ItemStack, playerIn: EntityPlayer, target: EntityLivingBase, hand: EnumHand): Boolean {
        target.entityData.setTag("CustomName", NBTTagString(MOD_NAME))
        target.entityData.setTag("CustomNameVisible", NBTTagByte(1))
        return super.itemInteractionForEntity(stack, playerIn, target, hand)
    }
}