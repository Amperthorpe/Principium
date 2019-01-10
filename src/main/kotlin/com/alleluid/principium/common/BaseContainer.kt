package com.alleluid.principium.common

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

abstract class BaseContainer(playerInv: InventoryPlayer) : Container(){
    abstract override fun canInteractWith(playerIn: EntityPlayer): Boolean
    init {
        // Player inventory slots
        for (i in 0..2){
            for (j in 0..8){
                this.addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        for (k in 0..8){
            this.addSlotToContainer(Slot(playerInv, k, 8 + k * 18, 142))
        }
    }

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