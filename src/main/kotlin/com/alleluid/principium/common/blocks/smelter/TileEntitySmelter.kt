package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.common.blocks.BaseInventoryTileEntity
import com.alleluid.principium.common.items.basic.*
import com.alleluid.principium.common.misc.SmelterFields
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable
import net.minecraftforge.items.ItemStackHandler

class TileEntitySmelter : BaseInventoryTileEntity("tile_entity_smelter", 3), ITickable
{
    val slotInput = 0
    val slotFuel = 1
    val slotOutput = 2

    private var _potential = 0
    var potential: Int = _potential
        get() = _potential
        set(value) {
            field = value
            markDirty()
        }
    var potentialCapacity = 50_000
    var maxReceive = 1000
    var maxExtract = 1000

    var isProcessing = false
    // Out of 1000
    var processingProgress = 0

    fun canExtract() = this.maxExtract > 0
    fun canReceive() = this.maxReceive > 0

    fun extractPotential(maxExtract: Int, simulate: Boolean = false): Int {
        if (!canExtract())
            return 0

        val potentialExtracted = Math.min(_potential, Math.min(this.maxExtract, maxExtract))
        if (!simulate)
            _potential -= potentialExtracted
        return potentialExtracted
    }

    fun receivePotential(maxReceive: Int, simulate: Boolean = false): Int {
        if (!canReceive())
            return 0

        val potentialReceived = Math.min(potentialCapacity - _potential, Math.min(this.maxReceive, maxReceive))
        if (!simulate)
            _potential += potentialReceived
        return potentialReceived
    }

    override fun update() {
        val fuel = inventory.getStackInSlot(slotFuel)
        if (world.totalWorldTime % 20 == 0L && !fuel.isEmpty){
            if (receiveEnergy(1000, false) > 0){
                if (!world.isRemote)
                    fuel.shrink(1)
            }
        }

//        val fuelValue = 100
//        val fuel = inventory.getStackInSlot(slotFuel)
//        if (!fuel.isEmpty && fuel.item == ModItems.ItemSubstruct && energy + fuelValue < maxEnergyStored){
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
                1 -> stack.item is ItemSubstruct
                2 -> false
                else -> false
            }
        }
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag("inventory", inventory.serializeNBT())
        compound.setInteger("energy", _potential)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        _potential = compound.getInteger("energy")
        super.readFromNBT(compound)
    }

}

