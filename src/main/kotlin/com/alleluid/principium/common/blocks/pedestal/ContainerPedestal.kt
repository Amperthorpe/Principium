package com.alleluid.principium.common.blocks.pedestal

import com.alleluid.principium.common.BaseContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerPedestal(playerInv: InventoryPlayer, val pedestal: TileEntityPedestal) : BaseContainer(playerInv, pedestal){
    init {
        val inventory = pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        addSlotToContainer(object : SlotItemHandler(inventory, 0, 80, 35) {
            override fun onSlotChanged() {
                pedestal.markDirty()
            }
        })
    }

    override fun canInteractWith(playerIn: EntityPlayer): Boolean = true
}