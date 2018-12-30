package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

object LaserDrill : ItemPickaxe(PrincipiumMod.principicToolMaterial) {
    private const val breakCooldown = 8
    private const val carefulReach = 10.0

    init {
        creativeTab = PrincipiumMod.creativeTab
        registryName = ResourceLocation(MOD_ID, "laser_drill")
        unlocalizedName = "$MOD_ID.laser_drill"
    }

    fun registerItemModel() = PrincipiumMod.proxy.registerItemRenderer(this, 0, "laser_drill")

    override fun getDestroySpeed(stack: ItemStack, state: IBlockState): Float = 1600f
    override fun canItemEditBlocks(): Boolean = true
    override fun canHarvestBlock(state: IBlockState, stack: ItemStack): Boolean = true

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("Instant Pickaxe + Laser Deletion Gun")
        tooltip.add("Left click to burst mine, right click is single block Silk Touch.")
    }

    override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        if (stack.tagCompound == null) {
            stack.tagCompound = NBTTagCompound()
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected)
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        if (worldIn.isRemote) {
            val stack = playerIn.getHeldItem(handIn)
            if (stack.hasTagCompound()) {
                var didBreak = false
                if (stack.tagCompound!!.getLong("timeWhenUsable") < worldIn.totalWorldTime) {
                    val look = playerIn.rayTrace(carefulReach, 1f)
                            ?: return super.onItemRightClick(worldIn, playerIn, handIn)
                    val pos = look.blockPos
                    val state = worldIn.getBlockState(pos)
                    if (playerIn.isSneaking) {

                    } else {
                        if (worldIn.isAirBlock(pos) || state.getBlockHardness(worldIn, pos) < 0) return super.onItemRightClick(worldIn, playerIn, handIn)
                        didBreak = true
                        val itemToCollect: ItemStack = if (state.block.canSilkHarvest(worldIn, pos, state, playerIn)) {
                            ItemStack(Item.getItemFromBlock(state.block), 1)
                        } else {
                            ItemStack(
                                    state.block.getItemDropped(state, Random(), 0),
                                    state.block.quantityDropped(Random())
                            )
                        }
                        val itemAttempt = playerIn.addItemStackToInventory(itemToCollect)
                        if (!itemAttempt) playerIn.entityDropItem(itemToCollect, 0f)?.setNoPickupDelay()
                        worldIn.destroyBlock(pos, false)
                    }
                }
                if (didBreak) {
                    stack.tagCompound!!.setLong("timeWhenUsable",
                            worldIn.totalWorldTime + breakCooldown)
                }
            }


        }
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}