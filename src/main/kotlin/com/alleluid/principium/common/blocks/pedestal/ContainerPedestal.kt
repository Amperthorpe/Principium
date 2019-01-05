package com.alleluid.principium.common.blocks.pedestal

import com.alleluid.principium.common.BaseContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerPedestal(playerInv: InventoryPlayer, val pedestal: TileEntityPedestal) : BaseContainer(){
    init {
        val inventory = pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        addSlotToContainer(object : SlotItemHandler(inventory, 0, 80, 35) {
            override fun onSlotChanged() {
                pedestal.markDirty()
            }
        })

        for (i in 0..2){
            for (j in 0..8){
                addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        for (k in 0..8){
            addSlotToContainer(Slot(playerInv, k, 8 + k * 18, 142))
        }
    }

    override fun canInteractWith(p0: EntityPlayer): Boolean = true
}