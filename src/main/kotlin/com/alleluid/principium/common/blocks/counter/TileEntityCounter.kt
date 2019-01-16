package com.alleluid.principium.common.blocks.counter

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

class TileEntityCounter : TileEntity(){

    var count: Int = 0
        set(value) {
            field = value
            markDirty()
        }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("count", count)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        count = compound.getInteger("count")
        super.readFromNBT(compound)
    }
}