package com.alleluid.principium.common.items

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object Debugger : BaseItem("debugger"){
    init {
        loreText.addNamedKey("lore1") // "Pay no attention to the item behind the curtain"
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (worldIn.isRemote) {
            val te = worldIn.getTileEntity(pos)
            println(te?.tileData.toString())
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (handIn == EnumHand.OFF_HAND)
            println(this.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, playerIn.heldItemMainhand))
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

}