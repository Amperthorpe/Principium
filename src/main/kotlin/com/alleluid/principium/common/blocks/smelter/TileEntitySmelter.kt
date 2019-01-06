package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.Utils
import com.alleluid.principium.common.blocks.BaseInventoryTileEntity
import com.alleluid.principium.common.items.ModItems
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagLong
import net.minecraft.util.ITickable
import net.minecraftforge.items.ItemStackHandler
import kotlin.math.abs

class TileEntitySmelter : BaseInventoryTileEntity("tile_entity_smelter", 3), ITickable {
    val maxPotentia = 300L
    var potentia = 0L

    private fun setPotentia(value: Long): Long {
        if (potentia + value <= maxPotentia) {
            potentia = value
            return 0L
        } else {
            val previousVal = potentia
            potentia = maxPotentia
            return abs((maxPotentia - previousVal) - value)
        }
    }

    private infix fun addPotentia(value: Long): Long {
        return setPotentia(value + potentia)
    }

    companion object {
        const val SLOT_INPUT = 0
        const val SLOT_FUEL = 1
        const val SLOT_OUTPUT = 2

        //Item meta to amount of potentia
        val fuelValues = mapOf<Int, Long>(
                0 to 10,
                1 to 25,
                2 to 50,
                3 to 100
        )
    }

    override fun update() {
        if (!world.isRemote) {
            val fuelStack = inventory.getStackInSlot(SLOT_FUEL)

            if (!fuelStack.isEmpty) {
                for (i in 1..fuelStack.count) {
                    val leftOver = addPotentia(fuelValues[fuelStack.metadata] ?: 0)
                    if (leftOver > 0) {
                        break
                    } else {
                        inventory.extractItem(SLOT_FUEL, 1, false)
                    }
                }
                Utils.chatMessage("Machine has $potentia P")
            }
        }
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
        compound.setTag("potentia", NBTTagLong(this.potentia))
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        potentia = compound.getLong("potentia")
        super.readFromNBT(compound)
    }

}

