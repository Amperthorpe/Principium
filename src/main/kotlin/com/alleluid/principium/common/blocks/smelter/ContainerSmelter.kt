package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.BaseContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IContainerListener
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.NonNullList
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerSmelter(val player: EntityPlayer, val smelter: TileEntitySmelter) : BaseContainer(player.inventory, smelter) {
    val energy
        get() = smelter.energyStored

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

        playerInventorySetup(0, 0)

    }


    override fun canInteractWith(playerIn: EntityPlayer) = true
}