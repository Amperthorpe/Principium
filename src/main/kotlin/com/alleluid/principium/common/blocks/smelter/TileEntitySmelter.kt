package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.blocks.BaseInventoryTileEntity
import com.alleluid.principium.common.items.ModItems
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagInt
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

    var timer = 0
    override fun update() {
        val fuel = inventory.getStackInSlot(SLOT_FUEL)
        if (world.totalWorldTime % 20 == 0L && !fuel.isEmpty){
            receiveEnergy(1000, false)
        }

//        val fuelValue = 100
//        val fuel = inventory.getStackInSlot(SLOT_FUEL)
//        if (!fuel.isEmpty && fuel.item == ModItems.itemSubstruct && energy + fuelValue < maxEnergyStored){
//            val quotient = (fuel.count * fuelValue) / maxEnergyStored
//            receiveEnergy(fuelValue * quotient, false)
//            fuel.shrink(quotient)
//        }
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
        compound.setInteger("energy", energy)
        return super.writeToNBT(compound)
    }

    fun debugMe(): Int{
        this.tileData.setInteger("energy", energy)
        return this.tileData.getInteger("energy")
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        energy = compound.getInteger("energy")
        super.readFromNBT(compound)
    }

}

