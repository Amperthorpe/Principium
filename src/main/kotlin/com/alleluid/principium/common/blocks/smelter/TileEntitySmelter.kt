package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.blocks.BaseInventoryTileEntity
import com.alleluid.principium.common.items.ModItems
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.items.ItemStackHandler

class TileEntitySmelter : BaseInventoryTileEntity("tile_entity_smelter", 3), ITickable, IEnergyStorage
{
    val SLOT_INPUT = 0
    val SLOT_FUEL = 1
    val SLOT_OUTPUT = 2

    @JvmField var energy = 0
    @JvmField val capacity = 100_000
    @JvmField var maxReceive = 1000
    @JvmField var maxExtract = 1000

    override fun canExtract() = this.maxExtract > 0
    override fun canReceive() = this.maxReceive > 0
    override fun getMaxEnergyStored() = this.capacity
    override fun getEnergyStored() = this.energy


    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        if (!canExtract())
            return 0

        val energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract))
        if (!simulate)
            energy -= energyExtracted
        return energyExtracted
    }

    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        if (!canReceive())
            return 0

        val energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive))
        if (!simulate)
            energy += energyReceived
        return energyReceived
    }


    override fun update() {
    }

    override val inventory = object : ItemStackHandler(3) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            markDirty()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            return when (slot) {
                0 -> true
                1 -> stack.item == ModItems.itemSubstruct
                2 -> false
                else -> false
            }
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

}

