package com.alleluid.principium.common.blocks.pedestal

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.Utils
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

class TileEntityPedestal : TileEntity(){
    init {
        GameRegistry.registerTileEntity(this::class.java, ResourceLocation(MOD_ID, "tile_entity_pedestal"))
    }

    private val inventory = object : ItemStackHandler(1) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            markDirty()
        }
    }


    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag("inventory", inventory.serializeNBT())
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        super.readFromNBT(compound)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as? T else super.getCapability(capability, facing)
    }
}
