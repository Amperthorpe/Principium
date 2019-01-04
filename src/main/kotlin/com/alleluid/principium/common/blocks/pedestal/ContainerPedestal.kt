package com.alleluid.principium.common.blocks.pedestal

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.*
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerPedestal(playerInv: InventoryPlayer, val pedestal: TileEntityPedestal) : Container(){
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

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        var itemstackCopy = ItemStack.EMPTY
        val slot = inventorySlots[index]

        if (slot != null && slot.hasStack){
            val itemstack1 = slot.stack
            itemstackCopy = itemstack1.copy()

            val containerSlots = inventorySlots.size - playerIn.inventory.mainInventory.size

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size, true)){
                    return ItemStack.EMPTY
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)){
                return ItemStack.EMPTY
            }

            if (itemstack1.count == 0){
                slot.putStack(ItemStack.EMPTY)
            } else {
                slot.onSlotChanged()
            }

            if (itemstack1.count == itemstackCopy.count) {
                return ItemStack.EMPTY
            }

            slot.onTake(playerIn, itemstack1)
        }
        return itemstackCopy
    }

}