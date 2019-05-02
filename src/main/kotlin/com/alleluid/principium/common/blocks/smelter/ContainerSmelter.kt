package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.BaseContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerSmelter(playerInv: InventoryPlayer, val smelter: TileEntitySmelter) : BaseContainer(playerInv, smelter) {
//    var maxPotentia = smelter.maxPotentia
//    var potentia = smelter.potentia
    init {
        val inventory = smelter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        addSlotToContainer(object : SlotItemHandler(inventory, 0, 77, 20) {
            override fun onSlotChanged() {
                smelter.markDirty()
            }
        })
        addSlotToContainer(object : SlotItemHandler(inventory, 1, 77, 57) {
            override fun onSlotChanged() {
                smelter.markDirty()
            }
        })
        addSlotToContainer(object : SlotItemHandler(inventory, 2, 124, 38) {
            override fun onSlotChanged() {
                smelter.markDirty()
            }
        })

    }

    override fun canInteractWith(playerIn: EntityPlayer) = true
}