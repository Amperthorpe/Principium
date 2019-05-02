package com.alleluid.principium.common

import com.alleluid.principium.common.blocks.BaseInventoryTileEntity
import com.alleluid.principium.Utils.ifClient
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity

abstract class BaseContainer(val playerInv: InventoryPlayer, val TEInv: BaseInventoryTileEntity) : Container(){
    abstract override fun canInteractWith(playerIn: EntityPlayer): Boolean

    open fun playerInventorySetup(xOffset: Int, yOffset: Int){
        for (i in 0..2){
            for (j in 0..8){
                this.addSlotToContainer(Slot(playerInv, j + i * 9 + 9,
                        xOffset + 8 + j * 18, yOffset + 84 + i * 18))
            }
        }

        for (k in 0..8){
            this.addSlotToContainer(Slot(playerInv, k,
                    xOffset + 8 + k * 18, yOffset + 142))
        }

    }

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        playerIn.world.ifClient { println(index) }
        var itemstackCopy = ItemStack.EMPTY
        val slot = inventorySlots[index]

        if (slot != null && slot.hasStack) {
            val itemstack1 = slot.stack
            itemstackCopy = itemstack1.copy()

            if (index < this.TEInv.size) {
                if (!mergeItemStack(itemstack1, this.TEInv.size, this.inventorySlots.size, true))
                    return ItemStack.EMPTY
            } else if (!this.TEInv.inventory.isItemValid(index, itemstack1)) {
                return ItemStack.EMPTY
            } else if (!this.mergeItemStack(itemstack1, 0, this.TEInv.size, false))
                return ItemStack.EMPTY

            if (itemstack1.isEmpty)
                slot.putStack(ItemStack.EMPTY)
            else
                slot.onSlotChanged()
        }

        return itemstackCopy
    }


}