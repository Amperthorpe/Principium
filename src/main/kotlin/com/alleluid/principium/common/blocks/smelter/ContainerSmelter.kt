package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.PacketHandler
import com.alleluid.principium.SmelterSyncMessage
import com.alleluid.principium.common.BaseContainer
import com.alleluid.principium.common.misc.SmelterFields
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerSmelter(val player: EntityPlayer, val smelterTile: TileEntitySmelter) : BaseContainer(player.inventory, smelterTile) {
    var fields = SmelterFields()

    init {
        val inventory = smelterTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        addSlotToContainer(object : SlotItemHandler(inventory, 0, 77, 20) {
            override fun onSlotChanged() {
                smelterTile.markDirty()
            }
        })
        addSlotToContainer(object : SlotItemHandler(inventory, 1, 77, 57) {
            override fun onSlotChanged() {
                smelterTile.markDirty()
            }
        })
        addSlotToContainer(object : SlotItemHandler(inventory, 2, 124, 38) {
            override fun onSlotChanged() {
                smelterTile.markDirty()
            }
        })

        playerInventorySetup(0, 0)
    }

    override fun updateProgressBar(id: Int, data: Int) {
        super.updateProgressBar(id, data)
    }

    override fun detectAndSendChanges() {
//        println("${if (world.isRemote) "client" else "server"} | $potential")
        super.detectAndSendChanges()
        var needUpdate = false
        val newFields = smelterTile.getFields()
        if (fields != newFields) {
            fields = newFields
            needUpdate = true
        }
        if (needUpdate) listeners.stream().filter { it is EntityPlayerMP }.forEach {
            PacketHandler.INSTANCE.sendTo(SmelterSyncMessage(fields), it as EntityPlayerMP)
        }
    }

    fun updateFields(fields: SmelterFields){
        smelterTile.setFields(fields)
    }

    override fun canInteractWith(playerIn: EntityPlayer) = true
}